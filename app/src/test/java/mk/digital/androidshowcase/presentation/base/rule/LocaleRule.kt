package mk.digital.androidshowcase.presentation.base.rule

import org.junit.rules.TestRule
import org.junit.runners.model.Statement
import org.junit.runner.Description
import java.util.Locale



class LocaleRule : TestRule {
    override fun apply(statement: Statement?, description: Description?): Statement = object : Statement() {
        override fun evaluate() {
            try {
                Locale.setDefault(Locale.forLanguageTag("en-US"))
                statement?.evaluate()
            } finally {
                Locale.setDefault(Locale.getDefault())
            }
        }
    }
}