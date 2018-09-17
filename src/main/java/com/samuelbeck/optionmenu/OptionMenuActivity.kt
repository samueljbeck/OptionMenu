package com.samuelbeck.optionmenu

import android.app.Activity
import android.os.Bundle
import java.util.*

/*
 * Created by samueljbeck on 9/17/18.
 */
class OptionMenuActivity: Activity(), OptionMenuStringInterface {

    private lateinit var optionMenu: OptionMenu

    override fun optionMenuSelect(option: String) {
        when (option) {
            "option 1" -> {
                //option 1 selected
            }
            "option 2" -> {
                //option 2 selected
            }
        }//edit
        //delete
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)


        val options = ArrayList(Arrays.asList("option 1", "option 2"))
        optionMenu = OptionMenu(this, this as OptionMenuStringInterface, options, true, R.layout.option_item)

    }

}