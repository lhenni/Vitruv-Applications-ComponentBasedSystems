package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaMethodReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaMethodReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJParam(final Parameter uParam, final Integer value, final Method javaMethod) {
      return uParam;
    }
    
    public EObject getElement1(final Parameter uParam, final Integer value, final Method javaMethod, final OrdinaryParameter jParam, final org.emftext.language.java.classifiers.Class returnType) {
      return javaMethod;
    }
    
    public void update0Element(final Parameter uParam, final Integer value, final Method javaMethod, final OrdinaryParameter jParam, final org.emftext.language.java.classifiers.Class returnType) {
      if (((value).intValue() == 1)) {
        javaMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
      } else {
        javaMethod.setTypeReference(UmlToJavaHelper.createTypeReference(uParam.getType(), returnType));
      }
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Parameter uParam, final Integer value) {
      Operation _operation = uParam.getOperation();
      return _operation;
    }
    
    public EObject getCorrepondenceSourceReturnType(final Parameter uParam, final Integer value, final Method javaMethod, final OrdinaryParameter jParam) {
      Type _type = uParam.getType();
      return _type;
    }
  }
  
  public SetJavaMethodReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParam, final Integer value) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uParam = uParam;this.value = value;
  }
  
  private Parameter uParam;
  
  private Integer value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaMethodReturnTypeRoutine with input:");
    getLogger().debug("   Parameter: " + this.uParam);
    getLogger().debug("   Integer: " + this.value);
    
    Method javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(uParam, value), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	null);
    if (javaMethod == null) {
    	return;
    }
    registerObjectUnderModification(javaMethod);
    OrdinaryParameter jParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJParam(uParam, value, javaMethod), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (jParam == null) {
    	return;
    }
    registerObjectUnderModification(jParam);
    org.emftext.language.java.classifiers.Class returnType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceReturnType(uParam, value, javaMethod, jParam), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (returnType == null) {
    	return;
    }
    registerObjectUnderModification(returnType);
    // val updatedElement userExecution.getElement1(uParam, value, javaMethod, jParam, returnType);
    userExecution.update0Element(uParam, value, javaMethod, jParam, returnType);
    
    postprocessElements();
  }
}
