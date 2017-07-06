package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCollectionDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCollectionDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType umlType, final Repository pcmRepository, final CollectionDataType pcmType) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final DataType umlType) {
      Model _model = umlType.getModel();
      return _model;
    }
    
    public void update0Element(final DataType umlType, final Repository pcmRepository, final CollectionDataType pcmType) {
      EList<org.palladiosimulator.pcm.repository.DataType> _dataTypes__Repository = pcmRepository.getDataTypes__Repository();
      _dataTypes__Repository.add(pcmType);
    }
    
    public EObject getElement2(final DataType umlType, final Repository pcmRepository, final CollectionDataType pcmType) {
      return umlType;
    }
    
    public void updatePcmTypeElement(final DataType umlType, final Repository pcmRepository, final CollectionDataType pcmType) {
      String _name = umlType.getName();
      pcmType.setEntityName(_name);
    }
    
    public EObject getElement3(final DataType umlType, final Repository pcmRepository, final CollectionDataType pcmType) {
      return pcmType;
    }
    
    public String getTag1(final DataType umlType, final Repository pcmRepository, final CollectionDataType pcmType) {
      return "collection";
    }
  }
  
  public CreateCollectionDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateCollectionDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlType = umlType;
  }
  
  private DataType umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCollectionDataTypeRoutine with input:");
    getLogger().debug("   DataType: " + this.umlType);
    
    Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlType), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	null);
    if (pcmRepository == null) {
    	return;
    }
    registerObjectUnderModification(pcmRepository);
    CollectionDataType pcmType = RepositoryFactoryImpl.eINSTANCE.createCollectionDataType();
    userExecution.updatePcmTypeElement(umlType, pcmRepository, pcmType);
    
    // val updatedElement userExecution.getElement1(umlType, pcmRepository, pcmType);
    userExecution.update0Element(umlType, pcmRepository, pcmType);
    
    addCorrespondenceBetween(userExecution.getElement2(umlType, pcmRepository, pcmType), userExecution.getElement3(umlType, pcmRepository, pcmType), userExecution.getTag1(umlType, pcmRepository, pcmType));
    
    postprocessElements();
  }
}