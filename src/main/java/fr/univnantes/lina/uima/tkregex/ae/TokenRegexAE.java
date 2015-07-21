package fr.univnantes.lina.uima.tkregex.ae;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import fr.univnantes.lina.UIMAProfiler;
import fr.univnantes.lina.uima.tkregex.RegexOccurrence;
import fr.univnantes.lina.uima.tkregex.RecognitionHandler;
import fr.univnantes.lina.uima.tkregex.Rule;


public abstract class TokenRegexAE extends JCasAnnotator_ImplBase {

	public static final String TOKEN_REGEX_RULES = "TokenRegexRules";
	@ExternalResource(key = TOKEN_REGEX_RULES, mandatory = true)
	protected RegexListResource resource = null;
	
	private static final String NO_SET_LABEL = "_no_set_label";
	public static final String PARAM_SET_LABELS = "SetLabels";
	@ConfigurationParameter(name = PARAM_SET_LABELS, mandatory = false, defaultValue = NO_SET_LABEL)
	private String labelFeature = null;
	
	protected abstract void ruleMatched(JCas jCas, RegexOccurrence occurrence);
	protected void beforeRuleProcessing(JCas jCas) {}
	protected void afterRuleProcessing(JCas jCas) {}
	
	@Override
	public void process(final JCas jCas) throws AnalysisEngineProcessException {
		UIMAProfiler.getProfiler("AnalysisEngine").start(this, "process");
		beforeRuleProcessing(jCas);
		
		if(!this.labelFeature.equals(NO_SET_LABEL)) {
			// Must set labels
			FSIterator<Annotation> it = jCas.getAnnotationIndex(getIteratedType(jCas)).iterator();
			Feature feat = this.getIteratedType(jCas).getFeatureByBaseName(this.labelFeature);
			while (it.hasNext()) {
				Annotation word = (Annotation) it.next();
				word.setStringValue(
						feat, 
						this.resource.getMatchingLabelString(word));
			}
		}
		
		RecognitionHandler recognitionHandler = new RecognitionHandler() {
			@Override
			public void recognizedEpisode(RegexOccurrence episode) {
				UIMAProfiler.getProfiler("Regex rule names").hit(episode.getRule().getName(), episode.asPatternString());
				ruleMatched(jCas, episode);
			}
		};
		for (final Rule rule: this.resource.getRules()) {
			rule.getAutomaton().addRecognitionHandler(recognitionHandler);
			rule.getAutomaton().reset();
		}
		
		FSIterator<Annotation> it = jCas.getAnnotationIndex(getIteratedType(jCas)).iterator();
		while (it.hasNext()) {
			Annotation word = (Annotation) it.next();
			boolean allRulesFailed = true;
			for (Rule rule : this.resource.getRules()) {
				rule.getAutomaton().nextAnnotation(word);
				allRulesFailed &= rule.getAutomaton().currentInstancesNum() == 0;
			}
			if(allRulesFailed)
				allRulesFailed(jCas);
		}
		
		
		
		for (Rule rule : this.resource.getRules()) 
			rule.getAutomaton().finish();
		for (final Rule rule: this.resource.getRules()) 
			rule.getAutomaton().removeRecognitionHandler(recognitionHandler);
		
		
		afterRuleProcessing(jCas);
		UIMAProfiler.getProfiler("AnalysisEngine").stop(this, "process");
	}

	protected void allRulesFailed(JCas jCas) {}
	
	private Type getIteratedType(JCas cas) {
		return cas.getTypeSystem().getType(this.resource.getIteratedTypeDescription().getName());
	}
}
