package de.uni.freiburg.iig.telematik.jagal.ts.labeled;

import de.uni.freiburg.iig.telematik.jagal.ts.Event;
import de.uni.freiburg.iig.telematik.jagal.ts.labeled.abstr.AbstractLabeledTransitionRelation;

public class LabeledTransitionRelation extends AbstractLabeledTransitionRelation<LTSState,Event,Object>{
	
	
	public LabeledTransitionRelation(){
		super();
	}
	
	public LabeledTransitionRelation(LTSState source, LTSState target){
		super(source, target);
	}
	
	public LabeledTransitionRelation(LTSState source, LTSState target, Event event){
		super(source, target);
	}
	
}
