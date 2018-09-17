# OptionMenu
Quick and dirty option menu when you need a floating menu


##Setup

In the Activity add the interface OptionMenuStringInterface

If it is Kotlin:  

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
    


In the onCreate function:


        val options = ArrayList(Arrays.asList("option 1", "option 2"))
        optionMenu = OptionMenu(this, this as OptionMenuStringInterface, options, true, R.layout.option_item)
        





	