package tools.vitruv.applications.pcmumlclassjava.tests

import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.uml2.uml.Model
import org.junit.Ignore
import org.junit.Test
import org.palladiosimulator.pcm.repository.ParameterModifier
import org.palladiosimulator.pcm.repository.Repository
import org.palladiosimulator.pcm.repository.RepositoryFactory
import tools.vitruv.applications.pcmumlclass.DefaultLiterals
import tools.vitruv.applications.umljava.util.java.JavaStandardType
import tools.vitruv.applications.umljava.util.java.JavaVisibility

import static org.junit.Assert.*
import static tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaStandardType.*
import static tools.vitruv.applications.umljava.util.java.JavaTypeUtil.*

/**
 * Model creation tests, to test the set of transformations as a whole. 
 * The tests mainly try to validate if the transformations terminate. To see if the results are correct manual verification of the output is necessary.
 * 
 * Because of transitive change propagation and potential de-synchronization between view and model, it is necessary to stepwise simulate
 * the insertion of Uml/java-models and reload the view, in oder to achieve the wanted propagation of a correct ComponentRepository.
 * In a editor based creation process, this would automatically be done if the synchronization is called often enough (enough saves).
 * Because of the necessary reloads, the model is loaded (while the out-of-synch elements remain) and registered with new IDs in the UUID resolver.
 * This might make it necessary to provide the VM that runs the tests with additional heap space.
 * For the same reason, the uml-insertion test only uses a reduced repository model.
 */
class MediaStoreRepositoryCreationTest extends PcmUmlClassJavaApplicationTest {

    protected static val final Logger logger = Logger.getLogger(typeof(MediaStoreRepositoryCreationTest).simpleName);

//	private static val PCM_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms.repository"
	// all SEFFs removed because the TUID-generator failed for ResourceDemandParameters 
	private static val PCM_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms_noSEFF.repository" 
//	private static val UML_MEDIA_STORE_REPOSITORY_PATH = "resources/model/ms_repository_noSEFF_unedited.uml"
	private static val UML_MEDIA_STORE_REDUCED_PATH = "resources/model/ms_repository_reduced.uml"
	 
	private static val UML_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.uml"
	private static val PCM_GENERATED_MEDIA_STORE_MODEL_PATH = "model-gen/ms_repository.repository"
	
	private def createRepository(){
		val pcmRepo = RepositoryFactory.eINSTANCE.createRepository
		pcmRepo.entityName = "TestRepository"
		
		val pcmCompositeTypeDummy = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmRepo.dataTypes__Repository += pcmCompositeTypeDummy
		pcmCompositeTypeDummy.entityName = "TestCompositeTypeDummy"
		
		val pcmPrimitiveType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmRepo.dataTypes__Repository += pcmPrimitiveType_Collection
		pcmPrimitiveType_Collection.entityName = "TestPrimitiveType_Collection"
		pcmPrimitiveType_Collection.innerType_CollectionDataType = helper.PCM_INT
		
		val pcmCompositeType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmRepo.dataTypes__Repository += pcmCompositeType_Collection
		pcmCompositeType_Collection.entityName = "TestCompositeType_Collection"
		pcmCompositeType_Collection.innerType_CollectionDataType = pcmCompositeTypeDummy
		
		val pcmCompositeType = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmRepo.dataTypes__Repository += pcmCompositeType
		pcmCompositeType.entityName = "TestCompositeType"
		var pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmInnerDeclaration
		pcmInnerDeclaration.entityName = "primitiveAttribute"
		pcmInnerDeclaration.datatype_InnerDeclaration = helper.PCM_INT
		//second attribute with compositeType
		pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmInnerDeclaration
		pcmInnerDeclaration.entityName = "compositeAttribute"
		pcmInnerDeclaration.datatype_InnerDeclaration = pcmCompositeTypeDummy
		//third attribute with collection Type
		pcmInnerDeclaration = RepositoryFactory.eINSTANCE.createInnerDeclaration
		pcmCompositeType.innerDeclaration_CompositeDataType += pcmInnerDeclaration
		pcmInnerDeclaration.entityName = "collectionAttribute"
		pcmInnerDeclaration.datatype_InnerDeclaration = pcmCompositeType_Collection
		
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmRepo.interfaces__Repository += pcmInterface
		pcmInterface.entityName = "TestInterface"
		
		val pcmSignature = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmInterface.signatures__OperationInterface += pcmSignature
		pcmSignature.entityName = "testSignature_compositeCollection"
		pcmSignature.returnType__OperationSignature = pcmCompositeType_Collection
		val pcmParameter = RepositoryFactory.eINSTANCE.createParameter
		pcmSignature.parameters__OperationSignature += pcmParameter
		pcmParameter.parameterName = "testParameter_compositeCollection"
		pcmParameter.modifier__Parameter = ParameterModifier.IN
		pcmParameter.dataType__Parameter = pcmCompositeType_Collection
		
		val pcmSignature2 = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmInterface.signatures__OperationInterface += pcmSignature2
		pcmSignature2.entityName = "testSignature_primitiveCollection"
		pcmSignature2.returnType__OperationSignature = pcmPrimitiveType_Collection
		val pcmParameter2 = RepositoryFactory.eINSTANCE.createParameter
		pcmSignature2.parameters__OperationSignature += pcmParameter2
		pcmParameter2.parameterName = "testParameter_primitiveCollection"
		pcmParameter2.modifier__Parameter = ParameterModifier.IN
		pcmParameter2.dataType__Parameter = pcmPrimitiveType_Collection
		
		val pcmInterface2 = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmRepo.interfaces__Repository += pcmInterface2
		pcmInterface2.entityName = "TestInterface2"
		pcmInterface2.parentInterfaces__Interface += pcmInterface

		val pcmComponent = RepositoryFactory.eINSTANCE.createBasicComponent
		pcmRepo.components__Repository += pcmComponent
		pcmComponent.entityName = "TestComponent"
		val pcmProvided = RepositoryFactory.eINSTANCE.createOperationProvidedRole
		pcmComponent.providedRoles_InterfaceProvidingEntity += pcmProvided
		pcmProvided.entityName = "testProvidedRole"
		pcmProvided.providedInterface__OperationProvidedRole = pcmInterface
		val pcmRequired = RepositoryFactory.eINSTANCE.createOperationRequiredRole
		pcmComponent.requiredRoles_InterfaceRequiringEntity += pcmRequired
		pcmRequired.entityName = "testRequiredRole"
		pcmRequired.requiredInterface__OperationRequiredRole = pcmInterface
		
		return pcmRepo
	}
	
	@Test
	def void testMinimalRepository_PcmUmlJava_collectionTypeReplace() {
		userInteractor.addNextTextInput("") // uses default uml model path and name
		userInteractor.addNextSingleSelection(0) // uses default java collection type
		
		val pcmRepo = RepositoryFactory.eINSTANCE.createRepository
		pcmRepo.entityName = "TestRepository"
		
		val pcmCompositeTypeDummy = RepositoryFactory.eINSTANCE.createCompositeDataType
		pcmRepo.dataTypes__Repository += pcmCompositeTypeDummy
		pcmCompositeTypeDummy.entityName = "TestCompositeTypeDummy"
		
		val pcmPrimitiveType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmRepo.dataTypes__Repository += pcmPrimitiveType_Collection
		pcmPrimitiveType_Collection.entityName = "TestPrimitiveType_Collection"
		pcmPrimitiveType_Collection.innerType_CollectionDataType = helper.PCM_INT
		
		val pcmCompositeType_Collection = RepositoryFactory.eINSTANCE.createCollectionDataType
		pcmRepo.dataTypes__Repository += pcmCompositeType_Collection
		pcmCompositeType_Collection.entityName = "TestCompositeType_Collection"
		pcmCompositeType_Collection.innerType_CollectionDataType = pcmCompositeTypeDummy
		
		val pcmInterface = RepositoryFactory.eINSTANCE.createOperationInterface
		pcmRepo.interfaces__Repository += pcmInterface
		pcmInterface.entityName = "TestInterface"
		
		val pcmSignature2 = RepositoryFactory.eINSTANCE.createOperationSignature
		pcmInterface.signatures__OperationInterface += pcmSignature2
		pcmSignature2.entityName = "testSignature_primitiveCollection"
		pcmSignature2.returnType__OperationSignature = pcmPrimitiveType_Collection
		
		val pcmPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.PCM_REPOSITORY_FILE_NAME + DefaultLiterals.PCM_REPOSITORY_EXTENSION
		val umlPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME + DefaultLiterals.UML_EXTENSION
		createAndSynchronizeModel(pcmPath, pcmRepo)
		assertModelExists(pcmPath)
		assertModelExists(umlPath)
		
		pcmSignature2.returnType__OperationSignature = pcmCompositeType_Collection
		saveAndSynchronizeChanges(pcmRepo)
	}
	
	@Test
	def void testMediaStoreCreation_UmlInserted_reduced() {
		var umlRepo_forward = URI.createURI(UML_MEDIA_STORE_REDUCED_PATH).testResource.contents.head as Model
		
		simulateRepositoryInsertion_UML(umlRepo_forward, UML_GENERATED_MEDIA_STORE_MODEL_PATH, PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(UML_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)	
	}
	
	// REMARK: Currently failing due to incomplete Java UUIDs
	@Test
	@Ignore
	def void testMediaStoreCreation_JavaInserted_reducedAndManuallyReplicated() {		
		val REPOSITORY_PKG_NAME = "defaultRepository"
		val CONTRACTS_PKG_NAME = "contracts"
		val DATATYPES_PKG_NAME = "datatypes"
		val DATATYPE_NAME_AudioCollectionRequest = "AudioCollectionRequest"
		val DATATYPE_NAME_FileContent = "FileContent"
		val ATTRIBUTE_NAME_Count = "Count"
		val ATTRIBUTE_NAME_Size = "Size"
		val INTERFACE_NAME_IFileStorage = "IFileStorage"
		val METHOD_NAME_getFile = "getFile"
		val PARAMETER_NAME_audioRequest = "audioRequest"
		val INTERFACE_NAME_IMediaAccess = "IMediaAccess"
		val METHOD_NAME_upload = "upload"
		val PARAMETER_NAME_file = "file"
		val COMPONENT_PKG_NAME = "mediaAccess"
		val COMPONENT_IMPL_NAME = "MediaAccessImpl"
		val ATTRIBUTE_NAME = "requiredIFileStorage"
		
		userInteractor.addNextTextInput("repository") // uml model name
		userInteractor.addNextTextInput("model") // uml model path
		userInteractor.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORY_SYSTEM__REPOSITORY) // the package should correspond to a repository
		userInteractor.addNextTextInput("model/repository.repository") // pcm repository file
		var jPkg_Repo = createJavaPackageAsModel(REPOSITORY_PKG_NAME, null)
		saveAndSynchronizeChanges(jPkg_Repo)
		
		var jPkg_datatypes = getJavaPackage(REPOSITORY_PKG_NAME, DATATYPES_PKG_NAME)
		var jDt_FileContent = createJavaClassInPackage(jPkg_datatypes, DATATYPE_NAME_FileContent, JavaVisibility.PUBLIC, false, false)
		var jDt_AudioCollectionRequest = createJavaClassInPackage(jPkg_datatypes, DATATYPE_NAME_AudioCollectionRequest, JavaVisibility.PUBLIC, false, false)
		// It would be better to create and move the created classes, in order to test more changes, 
		// but I don't know how to do a move of the saved resources, without breaking the ResourceSet.
		var jAtt_Count = createJavaAttribute(ATTRIBUTE_NAME_Count, createJavaPrimitiveType(JavaStandardType.INT), JavaVisibility.PUBLIC, false, false)
		var jAtt_Size = createJavaAttribute(ATTRIBUTE_NAME_Size, createJavaPrimitiveType(JavaStandardType.INT), JavaVisibility.PUBLIC, false, false)
		jDt_AudioCollectionRequest.members += jAtt_Count
		jDt_AudioCollectionRequest.members += jAtt_Size
		saveAndSynchronizeChanges(jDt_AudioCollectionRequest)
		
		var jPkg_contracts = getJavaPackage(REPOSITORY_PKG_NAME, CONTRACTS_PKG_NAME)
		var jI_IFileStorage = createJavaInterfaceInPackage(jPkg_contracts, INTERFACE_NAME_IFileStorage, #[])
		var jDtRef_AudioCollectionRequest = createNamespaceReferenceFromClassifier(jDt_AudioCollectionRequest)
		var jParam_audioRequest = createJavaParameter(PARAMETER_NAME_audioRequest, jDtRef_AudioCollectionRequest)
		var jDtRef_FileContent = createNamespaceReferenceFromClassifier(jDt_FileContent)
		var jMeth_getFile = createJavaInterfaceMethod(METHOD_NAME_getFile, jDtRef_FileContent, #[jParam_audioRequest])
		jI_IFileStorage.members += jMeth_getFile
		saveAndSynchronizeChanges(jI_IFileStorage)
		var jI_IMediaAccess = createJavaInterfaceInPackage(jPkg_contracts, INTERFACE_NAME_IMediaAccess, #[])
		jDtRef_FileContent = createNamespaceReferenceFromClassifier(jDt_FileContent)
		var jParam_file = createJavaParameter(PARAMETER_NAME_file, jDtRef_FileContent)
		var jMeth_upload = createJavaInterfaceMethod(METHOD_NAME_upload, null, #[jParam_file])
		jI_IMediaAccess.members += jMeth_upload
		saveAndSynchronizeChanges(jI_IMediaAccess)
		
		// TODO java -> uml -> pcm test error
		// At the moment it is possible to create a pcm::BasicComponent by inserting a package into the repository-package.
		// But it is not possible to edit the generated ComponentImpl-java::Class, because the containing CompilationUnit
		// can not be loaded/UUID-registered in the view-ResourceSet. 
		userInteractor.addNextSingleSelection(DefaultLiterals.USER_DISAMBIGUATE_REPOSITORYCOMPONENT_TYPE__BASIC_COMPONENT) // the package should correspond to a BasicComponent
		var jPkg_mediaAccess = createJavaPackageAsModel(COMPONENT_PKG_NAME, jPkg_Repo)
		saveAndSynchronizeChanges(jPkg_mediaAccess)
		var jClass_MediaAccessImpl = getJavaClassFromCompilationUnit(COMPONENT_IMPL_NAME, REPOSITORY_PKG_NAME, COMPONENT_PKG_NAME) // TODO here it fails
		assertNotNull(jClass_MediaAccessImpl)
		var jIRef_IFileStorage = createNamespaceReferenceFromClassifier(jI_IFileStorage)// required
		var jIRef_IMediaAccess = createNamespaceReferenceFromClassifier(jI_IFileStorage)// provided
		jClass_MediaAccessImpl.implements += jIRef_IMediaAccess
		var jAtt_requiredIFileStorage = createJavaAttribute(ATTRIBUTE_NAME, jIRef_IFileStorage, JavaVisibility.PRIVATE, false, false)
		jClass_MediaAccessImpl.members += jAtt_requiredIFileStorage
		saveAndSynchronizeChanges(jClass_MediaAccessImpl)
	}
	
	// REMARK: Only for manual test execution
	@Test
	@Ignore
	def void testMediaStoreCreation_PcmInserted() {
		setInteractiveUserInteractor
		
		var pcmRepo_forward = URI.createURI(PCM_MEDIA_STORE_REPOSITORY_PATH).testResource.contents.head as Repository
		
		val umlPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME + DefaultLiterals.UML_EXTENSION // the default output if no input is given to the UserInteractor
		createAndSynchronizeModel(PCM_GENERATED_MEDIA_STORE_MODEL_PATH, pcmRepo_forward)
		assertModelExists(PCM_GENERATED_MEDIA_STORE_MODEL_PATH)
		assertModelExists(umlPath)	
	}
	
	// REMARK: Only for manual test execution
	@Test
	@Ignore
	def void testMinimalRepository_PcmUmlJava() {
		setInteractiveUserInteractor
		
		var pcmRepo_forward = createRepository
		val pcmPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.PCM_REPOSITORY_FILE_NAME + DefaultLiterals.PCM_REPOSITORY_EXTENSION
		val umlPath = DefaultLiterals.MODEL_DIRECTORY + "/" + DefaultLiterals.UML_MODEL_FILE_NAME + DefaultLiterals.UML_EXTENSION
		createAndSynchronizeModel(pcmPath, pcmRepo_forward)
		assertModelExists(pcmPath)
		assertModelExists(umlPath)
	}
	
}
