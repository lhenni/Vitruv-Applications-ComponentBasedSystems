package tools.vitruv.applications.umlclassumlcomponents.sharedutil

import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.internal.impl.PackageImpl

import static org.junit.Assert.*

class SharedIntegrationTestUtil {
	
	public static val OUTPUT_NAME = "model/model.uml"
	
	public static def Resource getTestModelResource(String fileName) {
		val path = "TestModels/" + fileName  
		val resourceSet = new ResourceSetImpl()
        val res = resourceSet.getResource(URI.createFileURI(path), true)
        return res
	} 

		
	public static def assertCountOfTypeInList(EList<PackageableElement> elementsList, Class<? extends NamedElement> umlType, int count) {
		val typeElements = elementsList.filter(umlType)
		assertEquals(count, typeElements.size)
	}
	
	public static def assertCountOfTypeInPackage(EList<PackageableElement> elementsList, int packageNumber, Class<? extends NamedElement> umlType, int count) {
		val packages = elementsList.filter(PackageImpl)
		val packagedElements = packages.get(packageNumber).packagedElements
		assertCountOfTypeInList(packagedElements, umlType, count)
	}
	
	
}