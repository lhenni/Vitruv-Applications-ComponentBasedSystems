package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.pcm2java.transformations

import com.google.common.collect.Sets
import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.EmptyEObjectMappingTransformation
import java.util.ArrayList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.types.TypeReference
import org.palladiosimulator.pcm.repository.OperationRequiredRole

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import static extension tools.vitruv.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaUtils

class OperationRequiredRoleMappingTransformation extends EmptyEObjectMappingTransformation {

	private val Logger logger = Logger.getLogger(OperationRequiredRoleMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return OperationRequiredRole
	}

	override setCorrespondenceForFeatures() {
		Pcm2JavaUtils.addEntityName2NameCorrespondence(featureCorrespondenceMap)
	}

	/**
	 * called when a operation Required role has been created
	 * Following things are done:
	 * 1) create field with interface type and name of required role in main class of the component/system which corresponds to the newly created interface
	 * 2) create constructor parameter in all constructors of the class
	 * 3) add assignment from contructor to newly created field
	 */
	override createEObject(EObject eObject) {
		val OperationRequiredRole operationRequiredRole = eObject as OperationRequiredRole
		val interfaceRequiringEntity = operationRequiredRole.requiringEntity_RequiredRole
		val jaMoPPClass = correspondenceModel.getCorrespondingEObjectsByType(interfaceRequiringEntity, Class).claimOne
		val opInterface = operationRequiredRole.requiredInterface__OperationRequiredRole
		val jaMoPPInterface = correspondenceModel.getCorrespondingEObjectsByType(opInterface, Interface).claimOne
		val List<EObject> newEObjects = new ArrayList

		if (null === jaMoPPInterface) {
			logger.info(
				"No corresponding Java Interface found for OperationInterface " + opInterface +
					" not created an field for the operation required role (yet)")
			return null
		}

		val name = operationRequiredRole.entityName
		val TypeReference type = Pcm2JavaUtils.createNamespaceClassifierReference(jaMoPPInterface)

		//create import
		val import = Pcm2JavaUtils.addImportToCompilationUnitOfClassifier(jaMoPPClass, jaMoPPInterface)
		newEObjects.add(import)

		//create field
		val field = Pcm2JavaUtils.createPrivateField(EcoreUtil.copy(type), name)
		jaMoPPClass.members.add(field)
		newEObjects.add(field)

		//create constructor if none exists
		if (jaMoPPClass.members.filter(typeof(Constructor)).nullOrEmpty) {
			Pcm2JavaUtils.addConstructorToClass(jaMoPPClass)
		}

		//add param to contructor
		for (ctor : jaMoPPClass.members.filter(typeof(Constructor))) {
			val Parameter newParam = Pcm2JavaUtils.createOrdinaryParameter(EcoreUtil.copy(type), name)
			ctor.parameters.add(newParam)
			val Statement asssignment = Pcm2JavaUtils.createAssignmentFromParameterToField(field, newParam)
			ctor.statements.add(asssignment)
			newEObjects.add(newParam)
		}
		return newEObjects
	}

	override removeEObject(EObject eObject) {
		return null
	}

	/**
	 * called when a operation required role has been changed.
	 */
	override updateSingleValuedNonContainmentEReference(EObject affectedEObject, EReference affectedReference,
		EObject oldValue, EObject newValue) {
		val orr = affectedEObject as OperationRequiredRole
		val orrInterface = orr.requiredInterface__OperationRequiredRole
		val orrComponent = orr.requiringEntity_RequiredRole
		if(oldValue == newValue || orrInterface == newValue || orrComponent == newValue){
			// if the value has not changed we do nothing
			return new ChangePropagationResult
		}
		
		val EObject[] oldEObjects = removeEObject(affectedEObject)
		for (oldEObject : oldEObjects) {
			correspondenceModel.removeCorrespondencesThatInvolveAtLeastAndDependend(oldEObject.toSet)
			if (null !== oldEObject.eContainer) {
				return new ChangePropagationResult
			}
			EcoreUtil.remove(oldEObject)
		}
		val EObject[] newEObjects = createEObject(affectedEObject)
		if (null !== newEObjects) {
			for (newEObject : newEObjects) {
				correspondenceModel.createAndAddCorrespondence(newEObject, affectedEObject)
			}
		}
		return new ChangePropagationResult
	}

	/**
	 * called when the name or ID of a OperationRequiredRole has been changed - rename parameter
	 */
	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val affectedEObjects = Pcm2JavaUtils.checkKeyAndCorrespondingObjects(affectedEObject, affectedAttribute,
			featureCorrespondenceMap, correspondenceModel)
		if (affectedEObjects.nullOrEmpty) {
			return new ChangePropagationResult 
		}
		val affectedField = affectedEObjects.filter(typeof(Field))
		Pcm2JavaUtils.updateNameAttribute(Sets.newHashSet(affectedField), newValue, affectedAttribute,
			featureCorrespondenceMap, correspondenceModel, true)
		val affectedParam = affectedEObjects.filter(typeof(Parameter))
			Pcm2JavaUtils.updateNameAttribute(Sets.newHashSet(affectedParam), newValue, affectedAttribute,
				featureCorrespondenceMap, correspondenceModel, true)
		return new ChangePropagationResult
	}
}
	