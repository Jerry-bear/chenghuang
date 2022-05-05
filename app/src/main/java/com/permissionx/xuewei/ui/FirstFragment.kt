package com.permissionx.xuewei.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.permissionx.xuewei.R
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

        listPath.add("https://th.bing.com/th/id/R.529dc5f84c868be06154f4813c95b8f1?rik=jTXgjv%2bPzk9V2w&riu=http%3a%2f%2f5b0988e595225.cdn.sohucs.com%2fimages%2f20190804%2ff6d569cfd8ce4a60b82360dfb4f98757.jpeg&ehk=3OBe2uyiBEtDNFdOM%2fEG%2b06EtPPVYscXuj8ML%2fVzSjM%3d&risl=&pid=ImgRaw&r=0&sres=1&sresct=1")
        listPath.add("https://th.bing.com/th/id/R.ddc0638af0c31bbb4950d8df8223e5b6?rik=Umc%2fx%2fcpJ9kjLQ&riu=http%3a%2f%2fwww.wjtsm.com%2frepository%2fimage%2f4def3926-246f-43d6-b406-e83b133f7e95.jpg&ehk=EG0OoTUvLe8SBHp2dk6CfeJlw6kOAV554ZEDtvA3YsU%3d&risl=&pid=ImgRaw&r=0")
        listPath.add("https://img-u-3.51miz.com/preview/muban/00/00/51/73/M-517364-958CB4FB.jpg-0.jpg")
        listTitle.add("家常菜")
        listTitle.add("海鲜")
        listTitle.add("烧烤")

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