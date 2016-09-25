package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaPackageEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public String getRetrieveTag1(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
      return expectedTag;
    }
    
    public EObject getCorrepondenceSourceJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
      return sourceElementMappedToPackage;
    }
  }
  
  private DeleteJavaPackageEffect.EffectUserExecution userExecution;
  
  public DeleteJavaPackageEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.DeleteJavaPackageEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.sourceElementMappedToPackage = sourceElementMappedToPackage;this.packageName = packageName;this.expectedTag = expectedTag;
  }
  
  private NamedElement sourceElementMappedToPackage;
  
  private String packageName;
  
  private String expectedTag;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaPackageEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.expectedTag);
    
    org.emftext.language.java.containers.Package javaPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, packageName, expectedTag), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(sourceElementMappedToPackage, packageName, expectedTag));
    if (javaPackage == null) {
    	return;
    }
    initializeRetrieveElementState(javaPackage);
    deleteObject(userExecution.getElement1(sourceElementMappedToPackage, packageName, expectedTag, javaPackage));
    
    preprocessElementStates();
    postprocessElementStates();
  }
}
