package tools.vitruv.applications.pcmumlclass.mapping

class TagLiterals {

	public static final String REPOSITORY_TO_CONTRACTS_PACKAGE = "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository"
	public static final String REPOSITORY_TO_DATATYPES_PACKAGE = "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository"
	public static final String REPOSITORY_TO_REPOSITORY_PACKAGE = "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository"

	public static final String COLLECTION_DATATYPE__PARAMETER = "collection-typed-parameter";
	public static final String COLLECTION_DATATYPE__PROPERTY = "collection-typed-property";
	public static final String PARAMETER__REGULAR_PARAMETER = "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_Parameter:parameter";
//	public static final String SYSTEM__SYSTEM_PACKAGE = "system-package";
	
	public static final String COMPOSITE_DATATYPE__CLASS = "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";
	public static final String DATATYPE__TYPE = "umlXpcmDatatypes_map_UML_and_PCM_correspondence_Class:class_with_CompositeDataType:type";	
	public static final String INTERFACE_TO_INTERFACE = "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";

	public static final String PROVIDED_ROLE__INTERFACE_REALIZATION = "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationProvidedRole:role"; 
	
	public static final String REQUIRED_ROLE__PARAMETER = "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationRequiredRole:role";
	public static final String REQUIRED_ROLE__PROPERTY = "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationRequiredRole:role";
	
	public static final String SIGNATURE__OPERATION = "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationSignature:operationSignature";
	public static final String SIGNATURE__RETURN_PARAMETER = "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationSignature:operationSignature";
	
	public static final String REPOSITORY_COMPONENT__PACKAGE = "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_RepositoryComponent:component";
	public static final String IPRE__IMPLEMENTATION = "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component"; // IPRE = InterfaceProvidingRequiringEntity
	public static final String IPRE__CONSTRUCTOR = "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_RepositoryComponent:component";
}
