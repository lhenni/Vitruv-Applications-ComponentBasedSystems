package edu.kit.ipd.sdq.vitruvius.codeintegration.change2command

import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.java2pcm.Change2CommandTransformingJavaToPcm

class Java2PCMIntegrationChange2CommandTransforming extends Change2CommandTransformingJavaToPcm {
	
	override protected setup() {
		addChangeProcessor(new CodeIntegrationChangeProcessor(userInteracting));
		super.setup();
	}
	
}