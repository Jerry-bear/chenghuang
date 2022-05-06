package com.permissionx.xuewei.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permissionx.xuewei.R
import com.permissionx.xuewei.pojos.ArticleItem
import com.permissionx.xuewei.pojos.HAdapter
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() ,OnBannerListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //注：在本地也可以，只不过适配器类型为Int，之后在add中直接输入例如R.drawable.dog即可
    private val listPath:ArrayList<String> = ArrayList()
    //放标题的集合
    private val listTitle:ArrayList<String> =ArrayList()
    private lateinit var banner :Banner
    private lateinit var rvH: RecyclerView
    private lateinit var hAdapter:HAdapter
    private val hListBean = mutableListOf<ArticleItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        banner = view.findViewById(R.id.banner)
        rvH = view.findViewById(R.id.article_rv)
        initView()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    //实现适配banner的适配器
    private fun initView() {
        for (i in (0..20)) {
            hListBean.add(ArticleItem("https://img-u-5.51miz.com/Templet/00/25/00/34/250034_3098eacc0eecdd9e913a53204e5abb68.jpg-1.jpg",
            "针灸推拿心得","2022-5-6","文章",
            "https://th.bing.com/th/id/R.2e3b8af2d80a1df5e59ab1c327e7cf1a?rik=ZkkPl44bRfFCAw&riu=http%3a%2f%2fd.lanrentuku.com%2fdown%2fpng%2f0907%2fTango-Emote%2fTango-Emote-17.png&ehk=witPtlmGc%2fdGE1PCRY0MjWHFCFEd1wJuLyNXcWPg6HU%3d&risl=&pid=ImgRaw&r=0",
            "王鹏源",
            "1145"))
        }
        rvH.layoutManager = object : LinearLayoutManager(context){}
        hAdapter = HAdapter(context,R.layout.article_item,hListBean)
        hAdapter.bindToRecyclerView(rvH)
        rvH.adapter = hAdapter
        listPath.add("https://www.bing.com/images/search?view=detailV2&ccid=SjFcxkpR&id=D81EC1D72524B011C8C0CAC03F7B320B65F9E20B&thid=OIP.SjFcxkpRhTo7nyvoq7AcBAEyDM&mediaurl=https%3a%2f%2fwww.scitechsoft.com%2fwp-content%2fuploads%2f2016%2f07%2fsuina.jpeg&exph=533&expw=800&q=%e6%8e%a8%e6%8b%bf&simid=608023049938078986&FORM=IRPRST&ck=EF1BEAAD519733AA8E7398F8BEEDB8E2&selectedIndex=2")
        listPath.add("https://www.bing.com/images/search?view=detailV2&ccid=JIWIq%2bXN&id=4969E47B0F95E80DB10718B73C16EB76AD98128F&thid=OIP.JIWIq-XNoXc9-FHcRSud1wHaEt&mediaurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.248588abe5cda1773df851dc452b9dd7%3frik%3djxKYrXbrFjy3GA%26riu%3dhttp%253a%252f%252fwww.natureconceptcm.com%252fuploads%252f8%252f4%252f2%252f7%252f84270004%252f1269692664_orig.jpg%26ehk%3dcyUem8Pcafs5hyG5%252bpehy9dGL2b01q9eBlHhgMA8ywI%253d%26risl%3d%26pid%3dImgRaw%26r%3d0&exph=651&expw=1024&q=%e9%92%88%e7%81%b8&simid=607994784759898219&FORM=IRPRST&ck=92800812E7CF43E1CE13DC269BB1E38F&selectedIndex=3")
        listPath.add("https://www.bing.com/images/search?view=detailV2&ccid=gMTtJlrX&id=10FCE55EA0FC7BBFC1B764037D301E68DD48F577&thid=OIP.gMTtJlrXLz-SoBxNU67oJAHaDs&mediaurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.80c4ed265ad72f3f92a01c4d53aee824%3frik%3dd%252fVI3WgeMH0DZA%26riu%3dhttp%253a%252f%252fpic.sc.chinaz.com%252ffiles%252fpic%252fpsd1%252f201611%252fpsd22414.jpg%26ehk%3dyE6L5a0rgwyVv6ASXn1cDJh6DVUGX%252btjKzc4BTmWt2o%253d%26risl%3d%26pid%3dImgRaw%26r%3d0&exph=299&expw=600&q=%e5%85%bb%e7%94%9f&simid=608035771631879622&FORM=IRPRST&ck=06D5729669E649F092467DFEFA6E7B33&selectedIndex=83")
        listPath.add("https://th.bing.com/th/id/R.cb509dbc4fdf25efdffcc7b10e189076?rik=udaoAve43paLDg&riu=http%3a%2f%2fp2.qhimg.com%2ft019963a611a09357d3.jpg&ehk=mXdv4SBAm0Uh26qig4mi0W4SUke%2ftZ%2bGLjVs8KWBkug%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1")
        listTitle.add("推拿")
        listTitle.add("针灸")
        listTitle.add("养生")
        listTitle.add("艾灸")

        val myLoader=GlideImageLoader()
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(myLoader)
        //设置图片网址或地址的集合
        banner.setImages(listPath)
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default)
        //设置轮播图的标题集合
        banner.setBannerTitles(listTitle)
        //设置轮播间隔时间
        banner.setDelayTime(3000)
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true)
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
            //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
            .setOnBannerListener(this)
            //必须最后调用的方法，启动轮播图。
            .start()

    }
    //图片轮播加载
    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any, imageView: ImageView?) {
            //Glide 加载图片，Fresco也好、加载本地图片也好，这个类功能就是加载图片
            Glide.with(context!!).load(path).into(imageView!!)
        }
    }
    override fun OnBannerClick(position: Int) {
        Toast.makeText(context, listTitle[position]+":"+listPath[position], Toast.LENGTH_SHORT).show()
    }
}