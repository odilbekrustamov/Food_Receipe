package com.example.foodreceipe.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodreceipe.R
import com.example.foodreceipe.adapter.ChekAdapter
import com.example.foodreceipe.database.ChekRepository
import com.example.foodreceipe.manager.AuthManager
import com.example.foodreceipe.manager.DatabaseManager
import com.example.foodreceipe.manager.StorageManager
import com.example.foodreceipe.manager.handler.DBUserHandler
import com.example.foodreceipe.manager.handler.StorageHandler
import com.example.foodreceipe.model.Chek
import com.example.foodreceipe.model.User
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter

class ProfileFragment : BaseFragment() {
    val TAG = ProfileFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    lateinit var iv_profile: ImageView
    lateinit var tv_fullname: TextView
    lateinit var tv_email: TextView
    var feeds = ArrayList<Chek>()

    var pickedPhoto: Uri? = null
    var allPhotos = ArrayList<Uri>()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser && feeds.size > 0) {
            getallCheks()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        tv_fullname = view.findViewById(R.id.tv_fullname)
        tv_email = view.findViewById(R.id.tv_email)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        iv_profile = view.findViewById(R.id.iv_profile)
        iv_profile.setOnClickListener {
            pickFishBunPhoto()
        }

        loadUserInfo()
        getallCheks()

        val iv_logout = view.findViewById<ImageView>(R.id.iv_logout)
        iv_logout.setOnClickListener {
            AuthManager.signOut()
            activity?.finish()
        }
    }

    /**
     * Pick photo using FishBun library
     */
    private fun pickFishBunPhoto() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhotos)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
                allPhotos =
                    it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH) ?: arrayListOf()
                pickedPhoto = allPhotos.get(0)
                uploadPickedPhoto()
            }
        }

    private fun uploadPickedPhoto(){
        showLoading(activity)
        StorageManager.uploadUserPhoto(pickedPhoto!!, object : StorageHandler{
            override fun onSuccess(userImg: String) {
                DatabaseManager.updateUserImage(userImg)
                dismissLoading()
                iv_profile.setImageURI(pickedPhoto)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }

        })
    }

    private fun loadUserInfo(){
        showLoading(activity)
        DatabaseManager.loadUser(AuthManager.currentUser()!!.uid, object : DBUserHandler{
            override fun onSuccess(user: User?) {
                if (user != null){
                    showUserInfo(user)
                    dismissLoading()
                }
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }


    private fun showUserInfo(user: User) {
        tv_fullname.text = user.fullname
        tv_email.text = user.email
        Glide.with(this).load(user.userImg)
            .placeholder(R.drawable.iv_percon)
            .error(R.drawable.iv_percon)
            .into(iv_profile)
    }


    private fun refreshAdapter(items: ArrayList<Chek>) {
        val adapter = ChekAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun getallCheks() {
        var repository = ChekRepository(requireContext())
        feeds.clear()
        feeds.addAll(repository.getCheks())
        refreshAdapter(feeds)
    }
}