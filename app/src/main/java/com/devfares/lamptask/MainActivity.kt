package com.devfares.lamptask

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.devfares.lamptask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isLampOn = false
    private var isLampBroken = false
    private var currentSmallLampIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLamp.setOnClickListener {
            if (isLampOffAndUnBroken()) turnOnLamp() else if (isLampOnAndUnBroken()) turnOffLamp()
            when (currentSmallLampIndex) {
                1 -> handleLightToSmallLamp(binding.firstTryImage)
                2 -> handleLightToSmallLamp(binding.secondTryImage)
                3 -> handleLightToSmallLamp(binding.thirdTryImage)
            }
        }

        binding.mainLamp.setOnLongClickListener {
            if (!isLampBroken){
                binding.mainLamp.setImageResource(R.drawable.ic_broken_lamp)
                handleSmallLampsIndices()
                currentSmallLampIndex++
                binding.retryBtn.visibility = View.VISIBLE
                isLampBroken = true
            }
            true
        }

        binding.retryBtn.setOnClickListener {
            binding.mainLamp.setImageResource(R.drawable.ic_dark_lamp)
            binding.retryBtn.visibility = View.GONE
            setFlagsToInitState()
        }

        binding.playAgain.setOnClickListener {
            setNewGameState()
            setAllLampsDark()
            setFlagsToInitState()
            currentSmallLampIndex = 1
        }
    }

    private fun handleLightToSmallLamp(imageView: ImageView) {
        if (isLampOffAndUnBroken())
            imageView.setImageResource(R.drawable.ic_dark_lamp)
        else if (isLampOnAndUnBroken())
            imageView.setImageResource(R.drawable.ic_light_lamp)
    }

    private fun handleSmallLampsIndices() {
        when (currentSmallLampIndex) {
            1 -> setBrokenForSmallLamp(binding.firstTryImage)
            2 -> setBrokenForSmallLamp(binding.secondTryImage)
            3 -> {
                setBrokenForSmallLamp(binding.thirdTryImage)
                setGameOverState()
            }
        }
    }

    private fun setFlagsToInitState() {
        isLampBroken = false
        isLampOn = false
    }

    private fun setAllLampsDark() {
        binding.firstTryImage.setImageResource(R.drawable.ic_dark_lamp)
        binding.secondTryImage.setImageResource(R.drawable.ic_dark_lamp)
        binding.thirdTryImage.setImageResource(R.drawable.ic_dark_lamp)
        binding.mainLamp.setImageResource(R.drawable.ic_dark_lamp)
    }

    private fun setNewGameState() {
        binding.playAgain.visibility = View.GONE
        binding.gameOverTxt.visibility = View.GONE
        binding.retryBtn.visibility = View.GONE
    }

    private fun setGameOverState() {
        binding.gameOverTxt.visibility = View.VISIBLE
        binding.playAgain.visibility = View.VISIBLE
        binding.retryBtn.visibility = View.GONE
        binding.mainLamp.setImageResource(R.drawable.ic_sad_imo)
    }

    private fun setBrokenForSmallLamp(imageView: ImageView) {
        imageView.setImageResource(R.drawable.ic_cross_broken_lamp)
    }

    private fun turnOffLamp() {
        binding.mainLamp.setImageResource(R.drawable.ic_dark_lamp)
        isLampOn = false
    }

    private fun turnOnLamp() {
        binding.mainLamp.setImageResource(R.drawable.ic_light_lamp)
        isLampOn = true
    }

    private fun isLampOnAndUnBroken() = isLampOn && !isLampBroken

    private fun isLampOffAndUnBroken() = !isLampOn && !isLampBroken
}



