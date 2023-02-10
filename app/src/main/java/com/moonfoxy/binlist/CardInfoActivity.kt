package com.moonfoxy.binlist

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.moonfoxy.binlist.databinding.ActivityCardInfoBinding
import com.moonfoxy.binlist.models.BinInfo

class CardInfoActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityCardInfoBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCardInfoBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val bin: BinInfo = intent.getParcelableExtra("bin")!!
        val binPhone = bin.info?.bank?.phone
        val binWebsite = bin.info?.bank?.url

        mBinding.binNumber.text = bin.bin
        mBinding.schemeValue.text =
            if (bin.info?.scheme !== null) bin.info?.scheme else "Not provided"
        mBinding.networkValue.text =
            if (bin.info?.brand !== null) bin.info?.brand else "Not provided"
        mBinding.cardLengthValue.text =
            if (bin.info?.number?.length !== null) bin.info?.number?.length.toString() else "Not provided"
        mBinding.luhnValue.text = if (bin.info?.number?.luhn == true) "YES" else "NO"
        mBinding.typeValue.text = if (bin.info?.type !== null) bin.info?.type else "Not provided"
        mBinding.prepaidValue.text = if (bin.info?.prepaid == true) "YES" else "NO"
        mBinding.countryValue.text =
            if (bin.info?.country?.name !== null) "${bin.info?.country?.emoji} ${bin.info?.country?.name}" else "Not provided"
        mBinding.bankValue.text =
            if (bin.info?.bank?.name !== null) "${bin.info?.bank?.name}, ${bin.info?.bank?.city}" else "Not provided"
        mBinding.bankWebsiteValue.text =
            if (binWebsite !== null) binWebsite else "Not provided"
        mBinding.bankPhoneValue.text =
            if (binPhone !== null) binPhone else "Not provided"

        if (binWebsite !== null) {
            mBinding.bankWebsiteValue.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://$binWebsite")
                startActivity(openURL)
            }
        }

        if (binPhone !== null) {
            mBinding.bankPhoneValue.setOnClickListener {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:$binPhone")
                startActivity(callIntent)
            }
        }

        val latitude = bin.info?.country?.latitude
        val longitude = bin.info?.country?.longitude
        if (latitude !== null && longitude !== null) {
            mBinding.countryValue.setOnClickListener {
                val geoUri = "geo:$latitude,$longitude"
                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                startActivity(mapIntent)
            }
        }
    }
}