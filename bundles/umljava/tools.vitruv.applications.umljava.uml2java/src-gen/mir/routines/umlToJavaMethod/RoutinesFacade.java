package mir.routines.umlToJavaMethod;

import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createJavaMethod(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp) {
    mir.routines.umlToJavaMethod.CreateJavaMethodRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaMethodRoutine(this.executionState, calledBy,
    	uClass, umlOp);
    effect.applyRoutine();
  }
  
  public void deleteJavaMethod(final Operation umlOp) {
    mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine effect = new mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    mir.routines.umlToJavaMethod.CreateJavaParameterRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaParameterRoutine(this.executionState, calledBy,
    	uMeth, umlParam);
    effect.applyRoutine();
  }
  
  public void deleteJavaParameter(final Parameter uParam) {
    mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine effect = new mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine(this.executionState, calledBy,
    	uParam);
    effect.applyRoutine();
  }
  
  public void changeJavaParameterType(final Parameter uParam, final Type uType) {
    mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine effect = new mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine(this.executionState, calledBy,
    	uParam, uType);
    effect.applyRoutine();
  }
  
  public void setJavaMethodReturnType(final Parameter uParam, final Integer value) {
    mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine effect = new mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine(this.executionState, calledBy,
    	uParam, value);
    effect.applyRoutine();
  }
  
  public void setStatic(final Feature uFeat) {
    mir.routines.umlToJavaMethod.SetStaticRoutine effect = new mir.routines.umlToJavaMethod.SetStaticRoutine(this.executionState, calledBy,
    	uFeat);
    effect.applyRoutine();
  }
  
  public void setJavaMethodAbstract(final Operation umlOp) {
    mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine effect = new mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void createJavaInterfaceMethod(final Operation umlOp) {
    mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void handleMultiplicityForJavaAttribute(final Property uAttribute) {
    mir.routines.umlToJavaMethod.HandleMultiplicityForJavaAttributeRoutine effect = new mir.routines.umlToJavaMethod.HandleMultiplicityForJavaAttributeRoutine(this.executionState, calledBy,
    	uAttribute);
    effect.applyRoutine();
  }
}
