package net.yested.bootstrap

import net.yested.ObservableInput
import net.yested.Div
import net.yested.createElement
import net.yested.with
import net.yested.Span
import jquery.jq
import jquery.JQuery
import net.yested.utils.FormatString
import net.yested.utils.Moment
import net.yested.utils.FormatStringBuilder
import net.yested.whenAddedToDom
import net.yested.utils.on
import org.w3c.dom.HTMLInputElement

//TODO: support Locales: http://momentjs.com/docs/#/i18n/
/**
 *
 * uses library: https://github.com/Eonasdan/bootstrap-datetimepicker/blob/master/build/js/bootstrap-datetimepicker.min.js
 */
 class DateField(formatter: FormatStringBuilder.()->FormatString) : ObservableInput<Moment?>() {

    val formatString = FormatStringBuilder().formatter().toString()

    private val inputElement = (createElement("input") with {
        setAttribute("type", "text")
        className = "form-control"
    }) as HTMLInputElement

    override val element =
            (Div() with {
                clazz = "input-group date"
                appendChild(inputElement)
                appendChild((Span() with {
                    clazz = "input-group-addon"
                    appendChild((Span() with { clazz = "glyphicon glyphicon-calendar"; style = "cursor: pointer;" }))
                }))
            }).element

    override fun clear() {
        data = null
    }

    protected var value: String
        get():String = inputElement.value
        set(value: String) {
            inputElement.value = value
        }

    override var data: Moment?
        get() = if (value.isEmpty()) null else Moment.parse(value, formatString)
        set(value) {
            this.value = if (value == null) "" else value.format(formatString)
        }

    fun init() {
        val param = object {
            val format = formatString
        }
        // Hack: datetimepicker cannot handle unknown parameters, and Kotlin add a $metadata$ property to every object
        js("delete param.\$metadata$")
        jq(element).datetimepicker(param)

        jq(element).on("dp.change", {
            onChangeListeners.forEach { it() }
            onChangeLiveListeners.forEach { it() }
        })

    }

    init {
        this.element.whenAddedToDom {
            init()
        }
    }
}

private @native fun JQuery.datetimepicker(param: Any? ): Unit = noImpl;