package tools.vitruv.applications.pcmjava.reconstructionintegration.repository;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.palladiosimulator.pcm.repository.Repository;

import tools.vitruv.framework.change.description.VitruviusChangeFactory;
import tools.vitruv.framework.metamodel.Metamodel;
import tools.vitruv.applications.pcmjava.util.PCMJavaRepositoryCreationUtil;
import tools.vitruv.domains.pcm.util.RepositoryModelLoader;
import tools.vitruv.extensions.constructionsimulation.traversal.ITraversalStrategy;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.tests.util.TestUtil;
import tools.vitruv.framework.vsum.InternalVirtualModel;

/**
 * Test class for the usage of traversal strategies.
 *
 * @author Sven Leonhardt, Benjamin Hettwer
 */

public class TraversalTestcase {

    private static Logger logger = Logger.getLogger(TraversalTestcase.class);

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {

        // load model
        final String path = "Testmodels/small_example.repository";
        // String path = "Testmodels/interface_inheritance.repository";
        final Resource r = RepositoryModelLoader.loadPCMResource(path);

        // traverse model and get ordered list of changes
        final Repository repo = (Repository) r.getContents().get(0);
        final ITraversalStrategy<Repository> traversal = new RepositoryTraversalStrategy();
        EList<VitruviusChange> changes = null;

        try {
            changes = traversal.traverse(repo, URI.createPlatformResourceURI(path, true), null);
        } catch (final UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // create syncManager
        final Iterable<Metamodel> metamodels = PCMJavaRepositoryCreationUtil.createPcmJamoppMetamodels();
        // TODO Create correct change2command transformings here
        //final Change2CommandTransformingProvidingImpl change2CommandTransformingProviding = new Change2CommandTransformingProvidingImpl();
        final InternalVirtualModel vsum = TestUtil.createVSUM(metamodels);

        final VitruviusChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        // propagate changes
        vsum.propagateChange(compositeChange);

        logger.info("Integration done");

    }
}