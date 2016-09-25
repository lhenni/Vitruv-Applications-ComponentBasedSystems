package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInterfaceMethodReturnTypeEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final InterfaceMethod interfaceMethod, final DataType returnType, final org.emftext.language.java.classifiers.Class returnTypeClass) {
      return interfaceMethod;
    }
    
    public void update0Element(final InterfaceMethod interfaceMethod, final DataType returnType, final org.emftext.language.java.classifiers.Class returnTypeClass) {
      final TypeReference returnTypeReference = Pcm2JavaHelper.createTypeReference(returnType, returnTypeClass);
      interfaceMethod.setTypeReference(returnTypeReference);
    }
    
    public EObject getCorrepondenceSourceReturnTypeClass(final InterfaceMethod interfaceMethod, final DataType returnType) {
      return returnType;
    }
  }
  
  private ChangeInterfaceMethodReturnTypeEffect.EffectUserExecution userExecution;
  
  public ChangeInterfaceMethodReturnTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod interfaceMethod, final DataType returnType) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.ChangeInterfaceMethodReturnTypeEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.interfaceMethod = interfaceMethod;this.returnType = returnType;
  }
  
  private InterfaceMethod interfaceMethod;
  
  private DataType returnType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInterfaceMethodReturnTypeEffect with input:");
    getLogger().debug("   InterfaceMethod: " + this.interfaceMethod);
    getLogger().debug("   DataType: " + this.returnType);
    
    org.emftext.language.java.classifiers.Class returnTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceReturnTypeClass(interfaceMethod, returnType), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(returnTypeClass);
    // val updatedElement userExecution.getElement1(interfaceMethod, returnType, returnTypeClass);
    userExecution.update0Element(interfaceMethod, returnType, returnTypeClass);
    
    preprocessElementStates();
    postprocessElementStates();
  }
}
