package fr.univnantes.lina.uima.tkregex.test.asserts;

import fr.univnantes.lina.uima.tkregex.Op;
import fr.univnantes.lina.uima.tkregex.matchers.StringArrayMatcher;
import org.apache.uima.cas.text.AnnotationFS;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.IterableAssert;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class StringArrayMatcherAssert extends CoveredTextMatcherAssert {
	private StringArrayMatcher actual;

	public StringArrayMatcherAssert(StringArrayMatcher stringArrayMatcher, Class<?> selfType) {
		super(stringArrayMatcher, selfType);
		this.actual = stringArrayMatcher;
	}

	public StringArrayMatcherAssert ignoresCase() {
		if(actual.getOperator() != Op.IN_IGNORE_CASE && actual.getOperator() != Op.NIN_IGNORE_CASE)
			failWithMessage("Expected matcher's operator to ignore case, but actually does not.");
		return this;
	}

	public StringArrayMatcherAssert doesNotIgnoreCase() {
		if(actual.getOperator() == Op.IN_IGNORE_CASE || actual.getOperator() == Op.NIN_IGNORE_CASE)
			failWithMessage("Expected matcher's operator to not ignore case, but actually does.");
		return this;
	}


	public StringArrayMatcherAssert hasOperator(Op op) {
		if(actual.getOperator().equals(op))
			failWithMessage("Expected matcher's operator to be <%s>. Got <%s>", op, actual.getOperator());
		return this;
	}

	public IterableAssert<Object> asValuesAssert() {
		return assertThat(actual.getValues());
	}

}
