package mk.digital.androidshowcase.presentation.base.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.TimeZone

class TimezoneRule(
    private val testTimeZone: TimeZone = TimeZone.getTimeZone("UTC")
) : TestRule {

    private var originalTimeZone: TimeZone? = null

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            try {
                setTestTimeZone()
                base.evaluate()
            } finally {
                resetTimeZone()
            }
        }

        private fun setTestTimeZone() {
            originalTimeZone = TimeZone.getDefault()
            TimeZone.setDefault(testTimeZone)
        }

        private fun resetTimeZone() {
            originalTimeZone?.let { TimeZone.setDefault(it) }
        }
    }
}
