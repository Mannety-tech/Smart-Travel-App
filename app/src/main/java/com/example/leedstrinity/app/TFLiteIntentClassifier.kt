package com.example.leedstrinity.app

import android.content.Context
import org.tensorflow.lite.Interpreter
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.io.InputStream

class TFLiteIntentClassifier(context: Context) {

    private val interpreter: Interpreter
    private val vocab: Map<String, Int>
    private val labelMap: Map<Int, String>

    init {
        // Load TFLite model
        val modelBuffer = loadModelFile(context, "intent_classifier.tflite")
        interpreter = Interpreter(modelBuffer)

        // Load vocab
        val vocabJson = loadJSON(context, "tfidf_vocab.json")
        vocab = vocabJson.keys().asSequence().associateWith { vocabJson.getInt(it) }

        // Load label map
        val labelJson = loadJSON(context, "label_map.json")
        labelMap = labelJson.keys().asSequence().associate { key ->
            key.toInt() to labelJson.getString(key)
        }
    }

    // ---------------------------------------------------------
    // LOAD MODEL FROM ASSETS
    // ---------------------------------------------------------
    private fun loadModelFile(context: Context, filename: String): ByteBuffer {
        val inputStream = context.assets.open(filename)
        val bytes = inputStream.readBytes()

        val buffer = ByteBuffer.allocateDirect(bytes.size)
        buffer.order(ByteOrder.nativeOrder())
        buffer.put(bytes)
        buffer.rewind()

        return buffer
    }

    // ---------------------------------------------------------
    // LOAD JSON FILES (VOCAB + LABEL MAP)
    // ---------------------------------------------------------
    private fun loadJSON(context: Context, filename: String): JSONObject {
        val inputStream: InputStream = context.assets.open(filename)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return JSONObject(jsonString)
    }

    // ---------------------------------------------------------
    // PREDICT INTENT
    // ---------------------------------------------------------
    fun predict(text: String): String {
        val inputVector = textToVector(text)

        val output = Array(1) { FloatArray(labelMap.size) }
        interpreter.run(inputVector, output)

        val probs = output[0]
        val maxIndex = probs.indices.maxByOrNull { probs[it] } ?: 0

        return labelMap[maxIndex] ?: "unknown"
    }

    // ---------------------------------------------------------
    // TEXT → VECTOR (TF‑IDF STYLE)
    // ---------------------------------------------------------
    private fun textToVector(text: String): Array<FloatArray> {
        val inputSize = vocab.size
        val vector = FloatArray(inputSize)

        val tokens = text.lowercase().split(" ")

        for (token in tokens) {
            val index = vocab[token]
            if (index != null && index < inputSize) {
                vector[index] = 1f
            }
        }

        return arrayOf(vector)
    }

}

