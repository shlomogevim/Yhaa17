package com.example.yhaa17

class Stam {


  /*
  * class MainActivity : AppCompatActivity() {


    val CURRENT_NUM = 20

    private var convList: ArrayList<Conversation>? = null
    private var adapter: ConvListAtapter? = null
    private var layoutManger: RecyclerView.LayoutManager? = null



   val fileNum=20
    private val filepath = "MyFileStorage"
    val TALKLIST = "talklist" + fileNum.toString()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)

        val intent = Intent(this, Main2Activity::class.java)
        startActivity(intent)

       /* saveDataToExternalStorage()

        initAll()
        recyclerView.layoutManager = layoutManger
        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()


        operateConverastion(Conversation(1,"stam","stam"))*/

    }



    fun saveDataToExternalStorage() {

        myExternalFile = File(getExternalFilesDir(filepath),"aa")




        val path= Environment.getExternalStorageState()
        try {
            myExternalFile = File(getExternalFilesDir(filepath), TALKLIST)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val gson = Gson()
       /* val jsonString = gson.toJson(talkingList)


        try {
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(jsonString.toByteArray())
            fileOutPutStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }*/
        // Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()
    }


    private fun initAll() {
        convList = arrayListOf()

        var st1 = ""
        var st2 = ""
        val st3 = "\n"
        for (i in 1..30) {

            when (i) {

                1 -> {
                    st1 = "כן ולא"
                    val st20 = "ישנם שלש תשובות"
                    val st21 = "כן, לא"
                    val st22 = "והתשובה השלישית היא כן ולא"
                    val st23 = "עליה השיחה"
                    st2 = st20 + st3 + st21 + st3 + st22 + st3 + st23
                }
                else -> {
                    st1 = "talk num. $i"
                    st2 = "still not prepared"
                }
            }
            val conv = Conversation(i, st1, st2)
            convList?.add(conv)
        }

        layoutManger = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        adapter = ConvListAtapter(convList!!, onClickListener =
        { conversation ->
            operateConverastion(conversation)

            // Toast.makeText(this, "You press on Item no ${conversation.numC}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun operateConverastion(conversation: Conversation) {
        var CURRENT_NUM = 0

        when (conversation.numC) {
            1 -> CURRENT_NUM = 20


        }

        val intent = Intent(this, AnimationScreen::class.java)
        intent.putExtra(FILE_NUM, CURRENT_NUM)
        startActivity(intent)
    }
}

/* private fun getFirstTalk() {


     val CURRENT_NUM=20


     *//* val CURRENT_FILE = "text/text"+CURRENT_NUM+".txt"
         val STYLE_FILE = "style/style11.txt"
         val ADAM = "-אדם-"
         val GOD = "-אלוהים-"
         lateinit var talkerList: ArrayList<Talker>
         var operateList = arrayListOf<List<Int>>()*//*
=======

       /* val CURRENT_NUM=20

>>>>>>> Stashed changes

        *//* val CURRENT_FILE = "text/text"+CURRENT_NUM+".txt"
         val STYLE_FILE = "style/style11.txt"
         val ADAM = "-אדם-"
         val GOD = "-אלוהים-"
         lateinit var talkerList: ArrayList<Talker>
         var operateList = arrayListOf<List<Int>>()*//*


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


            //  mainStartLayout.setOnClickListener {
            val intent=Intent(this,AnimationScreen::class.java)
            intent.putExtra(FILE_NUM,CURRENT_NUM)
            startActivity(intent)

            //   }
        */


    }

<<<<<<< Updated upstream
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


            //  mainStartLayout.setOnClickListener {
            val intent=Intent(this,AnimationScreen::class.java)
            intent.putExtra(FILE_NUM,CURRENT_NUM)
            startActivity(intent)

            //   }
        */




  *
  *
  *
  * */


}