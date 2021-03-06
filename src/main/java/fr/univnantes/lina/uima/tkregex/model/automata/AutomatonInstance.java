package fr.univnantes.lina.uima.tkregex.model.automata;

import com.google.common.collect.Lists;
import fr.univnantes.lina.uima.tkregex.model.matchers.LabelledAnnotation;
import org.apache.uima.cas.text.AnnotationFS;

import java.util.LinkedList;
import java.util.List;

public class AutomatonInstance implements Cloneable {
	private State current;
	private LinkedList<StateExploration> trace = Lists.newLinkedList();
	private boolean failed;
	private AutomatonEngine automatonEng;
	private EngineSafeGuard safeGuard;
	private int transitionCount;
	private int iterateCount;
	private int backtrackCount;
	private int failCount;

	/**
	 * A unique id that is cloned when an iteration altervative
	 * is forked.
	 *
	 * The reason for that instanceId is that there cannot be several episodes
	 * recognized by the same instance id.
	 */
	private int instanceId;

	AutomatonInstance(AutomatonEngine automatonEngine, State current, int instanceId, EngineSafeGuard safeGuard) {
		this.automatonEng = automatonEngine;
		this.current = current;
		this.failed = false;
		this.instanceId = instanceId;
		this.safeGuard = safeGuard;
		this.transitionCount = 0;
		this.backtrackCount = 0;
		this.iterateCount = 0;
		this.failCount = 0;

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
			if(se.getAnnotation() != null)
				retVal.add( new LabelledAnnotation(
							se.getAnnotation(),
							se.getTransition().getMatcher().getLabel(),
							se.getTransition().getMatcher().isIgnoreMatcher()
					)
				);
		}
		return new RegexOccurrence(automatonEng, retVal);
	}

	/**
	 * Creates a clone of the current automatonEng instance for
	 * iteration alternative purposes.
	 * @return
	 */
	public AutomatonInstance doClone() {
		AutomatonInstance clone = new AutomatonInstance(this.automatonEng, this.current, this.instanceId, this.safeGuard);
		clone.failed = this.failed;
		clone.transitionCount = this.transitionCount;
		clone.failCount = this.failCount;
		clone.iterateCount = this.iterateCount;
		clone.backtrackCount = this.backtrackCount;
		clone.trace = new LinkedList<>();
		for(StateExploration se:this.trace) {
			clone.trace.add(se.doClone());
		}
		return clone;
	}


	public AnnotationFS firstAnno() {
		for(StateExploration se:trace) {
			return se.getAnnotation();
		}
		return null;
	}

	public void propagateAnnotation(AnnotationFS annotation) {
		LinkedList<AnnotationFS> annotations = Lists.newLinkedList();
		annotations.add(annotation);
		propagateAnnotations(annotations);
	}

	private void propagateAnnotations(LinkedList<AnnotationFS> annotations) {
		TransitionIterator transitionIt = this.current.transitionIterator();
		iterate(annotations, transitionIt);
	}

	public void iterate(LinkedList<AnnotationFS> annotations,
						TransitionIterator transitionIt) {
		iterateCount ++;
//		System.out.format("it: %5d - fail: %5d - back: %5d - transition: %5d %n", iterateCount, failCount, backtrackCount, transitionCount);
		if(iterateCount > safeGuard.getMaxIterations())
			this.failed = true;

		if(annotations.isEmpty()) {
		} else {
			AnnotationFS a = annotations.getFirst();
			Transition t = null;
			while (transitionIt.hasNext() && transitionCount < safeGuard.getMaxTransitions()) {
				transitionCount++;
				Transition transition = transitionIt.next();
				if(doesAnnotationMatchTransition(a, transition) && this.trace.size() < safeGuard.getMaxEpisodeLength()) {
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
				if(this.automatonEng.getAutomaton().isAccepting(this.current))
					this.failed = true;
				else
					backtrack(annotations);
			} else {
				this.current = t.getToState();
				propagateAnnotations(annotations);
			}
		}
	}


	private boolean doesAnnotationMatchTransition(AnnotationFS a, Transition transition) {
		return transition.match(a);
	}

	private void backtrack(LinkedList<AnnotationFS> annotations) {
		if(trace.isEmpty() || backtrackCount > safeGuard.getMaxBacktrack()) {
			failCount ++;
			this.failed = true;
		} else {
			backtrackCount ++;
			StateExploration at = trace.removeLast();
			this.current = at.getTransition().getFromState();
			annotations.addFirst(at.getAnnotation());
			iterate(annotations, at.getIterator());
		}

	}

	public int getInstanceId() {
		return instanceId;
	}

	@Override
	public String toString() {
		return String.format("AutomatonInstance[id=%d,trace=%s]", instanceId, getEpisode());
	}
}
