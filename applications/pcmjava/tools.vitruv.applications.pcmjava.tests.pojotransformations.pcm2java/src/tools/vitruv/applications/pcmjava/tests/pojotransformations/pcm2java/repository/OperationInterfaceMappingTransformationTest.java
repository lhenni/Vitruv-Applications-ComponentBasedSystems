package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.junit.Test;

import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.PCM2JaMoPPTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.PCM2JaMoPPTestUtils;

public class OperationInterfaceMappingTransformationTest extends PCM2JaMoPPTransformationTest {

    @Test
    public void testAddInterface() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);

        final OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo,
                PCM2JaMoPPTestUtils.INTERFACE_NAME);

        this.assertOperationInterfaceCorrespondences(opInterface);
    }

    @Test
    public void testRenameInterface() throws Throwable {
        final Repository repo = this.createAndSyncRepository(this.resourceSet, PCM2JaMoPPTestUtils.REPOSITORY_NAME);
        OperationInterface opInterface = this.addInterfaceToReposiotryAndSync(repo, PCM2JaMoPPTestUtils.INTERFACE_NAME);

        opInterface = this.renameInterfaceAndSync(opInterface);

        this.assertOperationInterfaceCorrespondences(opInterface);
    }

    @SuppressWarnings("unchecked")
    private void assertOperationInterfaceCorrespondences(final OperationInterface opInterface) throws Throwable {
        this.assertCorrespondnecesAndCompareNames(opInterface, 2, new java.lang.Class[] { CompilationUnit.class,
                Interface.class }, new String[] { opInterface.getEntityName(), opInterface.getEntityName() });
    }
}