package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.modifiers.Private;
import org.emftext.language.java.modifiers.Protected;
import org.emftext.language.java.modifiers.Public;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class JavaElementVisibilityChangedReaction extends AbstractReactionRealization {
  private CreateEObject<Modifier> createChange;
  
  private InsertEReference<AnnotableAndModifiable, Modifier> insertChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    org.emftext.language.java.modifiers.AnnotableAndModifiable affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    org.emftext.language.java.modifiers.Modifier newValue = insertChange.getNewValue();
    int index = insertChange.getIndex();
    				
    getLogger().trace("Passed change matching of Reaction " + this.getClass().getName());
    if (!checkUserDefinedPrecondition(insertChange, affectedEObject, affectedFeature, newValue, index)) {
    	resetChanges();
    	return;
    }
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.javaToUmlMethod.RoutinesFacade routinesFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementVisibilityChangedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementVisibilityChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(insertChange, affectedEObject, affectedFeature, newValue, index, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<org.emftext.language.java.modifiers.Modifier> _localTypedChange = (CreateEObject<org.emftext.language.java.modifiers.Modifier>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.emftext.language.java.modifiers.Modifier)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<org.emftext.language.java.modifiers.Modifier>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchCreateChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertEReference<?, ?>) {
    	InsertEReference<org.emftext.language.java.modifiers.AnnotableAndModifiable, org.emftext.language.java.modifiers.Modifier> _localTypedChange = (InsertEReference<org.emftext.language.java.modifiers.AnnotableAndModifiable, org.emftext.language.java.modifiers.Modifier>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof org.emftext.language.java.modifiers.AnnotableAndModifiable)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("annotationsAndModifiers")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof org.emftext.language.java.modifiers.Modifier)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<org.emftext.language.java.modifiers.AnnotableAndModifiable, org.emftext.language.java.modifiers.Modifier>) change;
    	return true;
    }
    
    return false;
  }
  
  private boolean checkUserDefinedPrecondition(final InsertEReference insertChange, final AnnotableAndModifiable affectedEObject, final EReference affectedFeature, final Modifier newValue, final int index) {
    return (((newValue instanceof Public) || (newValue instanceof Private)) || (newValue instanceof Protected));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference insertChange, final AnnotableAndModifiable affectedEObject, final EReference affectedFeature, final Modifier newValue, final int index, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeUmlNamedElementVisibility(affectedEObject, newValue);
    }
  }
}
