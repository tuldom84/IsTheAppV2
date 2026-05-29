package com.dtz.netservice.ui.adapters.photoadapter

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.dtz.netservice.R
import com.dtz.netservice.data.model.Photo

import com.dtz.netservice.utils.ConstFun.setImageUrl
import androidx.core.content.ContextCompat
import android.widget.*
import com.dtz.netservice.preference.DataSharePreference.getSelectedItem
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by luis rafael on 20/03/18.
 */
class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val dateTime: TextView = itemView.findViewById(R.id.date_item_photo)
    val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    val itemClick: CardView = itemView.findViewById(R.id.item_click_photo)
    private val imgCheck: CircleImageView = itemView.findViewById(R.id.img_selected_photo)
    private val name: TextView = itemView.findViewById(R.id.name_item_photo)

    fun isSelected(key:String){
        if (itemView.context.getSelectedItem(key)){
            imgCheck.show()
            itemClick.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.colorSelected))
        }else{
            imgCheck.hide()
            itemClick.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.colorWhite))
        }
    }

    fun bind(item: Photo) = with(itemView) {
        dateTime.text = item.dateTime
        name.text = item.nameRandom
        imgPhoto.setImageUrl(item.urlPhoto!!,R.color.colorGrayTransp)
    }
}
