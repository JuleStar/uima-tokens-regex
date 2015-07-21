package fr.univnantes.lina.uima.tkregex;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.uima.cas.text.AnnotationFS;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Automaton implements Cloneable {
	

//	private String name;
	private Rule rule;
	private State initState;
	private TreeSet<State> acceptingStates;
	private TreeSet<State> states;
	
	private LinkedList<AutomatonInstance> instances;
	private Collection<RecognitionHandler> handlers = new LinkedList<RecognitionHandler>();
	
	Automaton(State initState,
			Set<State> acceptingStates, Set<State> states) {
		this();
		this.initState = initState;
		if(!states.contains(initState))
			throw new IllegalStateException("The states set must contains the init state.");
		if(!states.containsAll(acceptingStates))
			throw new IllegalStateException("The states set must contains all the accepting states.");
		this.acceptingStates = new TreeSet<State>(acceptingStates);
		this.states = new TreeSet<State>(states);
	}

	Automaton() {
		super();
		this.acceptingStates = new TreeSet<State>();
		this.states = new TreeSet<State>();
		reset();
	}
	
	void setRule(Rule rule) {
		this.rule = rule;
	}

	public Rule getRule() {
		return rule;
	}
	boolean addAllState(Collection<? extends State> arg0) {
		return acceptingStates.addAll(arg0);
	}

	public Set<State> getAcceptingStates() {
		return ImmutableSet.copyOf(acceptingStates);
	}
	
	public Set<State> getStates() {
		return ImmutableSet.copyOf(states);
	}


	public void addRecognitionHandler(RecognitionHandler handler) {
			this.handlers.add(handler);
	}
	
	public void removeRecognitionHandler(RecognitionHandler handler) {
		this.handlers.remove(handler);		
	}
	public void finish() {
		nextAnnotation(LastAnnotationToken.INSTANCE);
	}
	
	public void nextAnnotation(AnnotationFS annotation) {
		AutomatonInstance automatonInstance = new AutomatonInstance(this, initState);
		this.instances.add(automatonInstance);
		
		ListIterator<AutomatonInstance> instanceIt = this.instances.listIterator();
		RegexOccurrence matchingEpisode = null;
		while (instanceIt.hasNext()) {
			AutomatonInstance inst = instanceIt.next();
			
			/*
			 *  remove the instance if there is already a 
			 *  found instance covering it.
			 */
			if(matchingEpisode != null) {
				AnnotationFS firstAnno = inst.firstAnno();
				if(firstAnno != null) {
					List<LabelledAnnotation> matchingAnnoList = matchingEpisode.getAllMatchingAnnotations();
					AnnotationFS lastAnno = matchingAnnoList.get(matchingAnnoList.size()-1).getAnnotation();
					int begin1 = lastAnno.getBegin();
					int begin2 = firstAnno.getBegin();
					int end1 = lastAnno.getEnd();
					int end2 = firstAnno.getEnd();
					if (!(begin1 < begin2 || (begin1 == begin2 && end1 < end2))) {
						instanceIt.remove();
						continue;
					}
				} else {
					
				}
			}
			
			inst.propagateAnnotation(annotation);
			
			if(inst.hasFailed()) {
				instanceIt.remove();
				if(this.isAccepting(inst.getCurrentState())) {
					// The instance succeeds
					RegexOccurrence e = inst.getEpisode();
					matchingEpisode = e;
					notifyHandlers(e);
				}
			}
		}
	}
	
	public int currentInstancesNum() {
		return this.instances.size();
	}
		
	
	public void reset() {
		this.instances = Lists.newLinkedList();
	}
	
	void notifyHandlers(RegexOccurrence episode) {
//		episode.setRuleName(this.name);
		for(RecognitionHandler h:this.handlers) {
			h.recognizedEpisode(episode);
		}
	}
	
	void setInitState(State initState) {
		this.initState = initState;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	@Override
	public String toString() {
		String nullName = "unnamed " + System.identityHashCode(this);
//		return "Automaton " + (this.name == null ? nullName : this.name) + toFullString();
		return "Automaton " + nullName;
	}

	public String toFullString() {
		String str = "";
		for(State s: this.states) {
			str+="\n";
			str+=s.toString();
			if(this.initState.equals(s))
				str+=" INITIAL";
			if(this.acceptingStates.contains(s))
				str+=" ACCEPTING";
			str+=s.transitionsToString();
		}
		return str;
	}

//	public String getName() {
//		return name;
//	}
	
	public State getInitState() {
		return initState;
	}
	
	void addAcceptingState(State s) {
		this.acceptingStates.add(s);
	}
	
	public Automaton deepClone() {
		Automaton automataClone = new Automaton();
		Map<State, State> clones = Maps.newHashMap();
		
		// clone states
		for(State s: this.states) {
			State clone = new State();
			clones.put(s, clone);
		}
		
		// clone transitions
		for(State s: this.states) {
			State clone = clones.get(s);
			for(Transition t:s.getTransitions()) {
				State toState = clones.get(t.getToState());
				Transition nt;
				if(t.isEpsilon()) {
					nt = new EpsilonTransition();
				} else {
					nt = new Transition();
					nt.setMatcher(t.getMatcher());
				}
				nt.setFromState(clone);
				nt.setToState(toState);
				clone.addTransition(nt);
			}
		}
		
		automataClone.initState = clones.get(this.initState);

		for(State state:this.states) 
			automataClone.states.add(clones.get(state));

		for(State acceptingState:this.acceptingStates) {
			State state = clones.get(acceptingState);
			automataClone.acceptingStates.add(state);
		}
		
		return automataClone;
	}

	private Map<State, Boolean> acceptingStatesCache = Maps.newTreeMap();
	
	public boolean isAccepting(State state) {
		Boolean accept = acceptingStatesCache.get(state);
		if(accept == null ) {
			accept = computeAcceptingState(state);
			acceptingStatesCache.put(state,accept);
		}
		return accept;
	}
	
	private boolean computeAcceptingState(State s) {
		List<State> epsilonClosure = s.getEpsilonClosure();
		for(State st: epsilonClosure) {
			if(this.acceptingStates.contains(st))
				return true;
		}
		return false;
	}

	public boolean matchesEmptySequence() {
		return isAccepting(this.initState);
	}

	
}

class AutomatonInstance implements Cloneable {
	private State current;
	private LinkedList<StateExploration> trace = Lists.newLinkedList();
	private boolean failed;
	private Automaton automaton;
	
	AutomatonInstance(Automaton automaton, State current) {
		this.automaton = automaton;
		this.current = current;
		this.failed = false;
	}

	public boolean hasFailed() {
		return failed;
	}

	public State getCurrentState() {
		return current;
	}
	
	void setCurrentState(State current) {
		this.current = current;
	}
	
	RegexOccurrence getEpisode() {
		List<LabelledAnnotation> retVal = new LinkedList<LabelledAnnotation>();
		for(StateExploration se:trace) {
			if(se.annotation != null) 
				retVal.add( new LabelledAnnotation(
							se.annotation,
							se.transition.getMatcher().getLabel(),
							se.transition.getMatcher().isIgnoreMatcher()
					)
				);
		}
		return new RegexOccurrence(automaton, retVal);
	}
	
	private class StateExploration {
		private Iterator<Transition> iterator;
		private AnnotationFS annotation;
		private Transition transition;
		StateExploration(Iterator<Transition> iterator,
				AnnotationFS annotation, Transition transition) {
			super();
			this.iterator = iterator;
			this.annotation = annotation;
			this.transition = transition;
		}
	}
	
	public AnnotationFS firstAnno() {
		for(StateExploration se:trace) {
			return se.annotation;
		}
		return null;
	}

	public void propagateAnnotation(AnnotationFS annotation) {
		LinkedList<AnnotationFS> annotations = Lists.newLinkedList();
		annotations.add(annotation);
		propagateAnnotations(annotations);
	}
	
	private void propagateAnnotations(LinkedList<AnnotationFS> annotations) {
		Iterator<Transition> transitionIt = this.current.transitionIterator();
		iterate(annotations, transitionIt);
	}

	public void iterate(LinkedList<AnnotationFS> annotations,
			Iterator<Transition> transitionIt) {
		if(annotations.isEmpty()) {
		} else {
			AnnotationFS a = annotations.getFirst();
			Transition t = null;
			while (transitionIt.hasNext()) {
				Transition transition = transitionIt.next();
				if(transition.match(a)) {
					AnnotationFS matchedAnnotation = annotations.pop();
					
					this.trace.addLast(new StateExploration(
							transitionIt, 
							matchedAnnotation, 
							transition));
					t = transition;
					break;
				}
			}
			if(t==null) {// no matching transition
				
				// do not backtrack if at accepting state
				if(this.automaton.isAccepting(this.current))
					this.failed = true;
				else
					backtrack(annotations);
			} else {
				this.current = t.getToState();
				propagateAnnotations(annotations);
			}
		}
	}

	private void backtrack(LinkedList<AnnotationFS> annotations) {
		if(trace.isEmpty()) {
			this.failed = true;
		} else {
			StateExploration at = trace.removeLast();
			this.current = at.transition.getFromState();
			annotations.addFirst(at.annotation);
			iterate(annotations, at.iterator);
		}
		
	}
}
