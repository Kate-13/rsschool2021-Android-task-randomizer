package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigDecimal
import android.content.Context


class FirstFragment : Fragment() {


    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValue: EditText? = null
    private var maxValue: EditText? = null
    private var startSecondFragment: StartSecondFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startSecondFragment = context as? StartSecondFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValue = view.findViewById(R.id.min_value)
        maxValue = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val min = minValue?.text.toString()
            val max = maxValue?.text.toString()
            val minInt = min.toIntOrNull() ?: -1
            val maxInt = max.toIntOrNull() ?: -1
            when {
                min.isEmpty() || max.isEmpty() -> {
                    Toast.makeText(requireContext(), "Value is empty", Toast.LENGTH_SHORT).show()
                }
                BigDecimal(min) > BigDecimal(Int.MAX_VALUE) || BigDecimal(max) > BigDecimal(Int.MAX_VALUE)  -> {
                    Toast.makeText(requireContext(), "Value is too large", Toast.LENGTH_SHORT).show()
                }
                min == max -> {
                    Toast.makeText(requireContext(), "MIN = MAX", Toast.LENGTH_SHORT).show()
                }
                min.toInt() > max.toInt()  -> {
                    Toast.makeText(requireContext(), "MIN > MAX", Toast.LENGTH_SHORT).show()
                }
                min.toInt() < 0 || max.toInt() < 0 -> {
                    Toast.makeText(requireContext(), "Value < 0", Toast.LENGTH_SHORT).show()
                }
                else -> startSecondFragment?.openSecondFragment(min.toInt(), max.toInt())
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}