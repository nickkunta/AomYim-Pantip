package com.nantaphop.pantipfanapp.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nantaphop.pantipfanapp.BaseApplication;
import com.nantaphop.pantipfanapp.R;
import com.nantaphop.pantipfanapp.event.OpenTopicEvent;
import com.nantaphop.pantipfanapp.response.Tag;
import com.nantaphop.pantipfanapp.response.Topic;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by nantaphop on 21-Jan-15.
 */
@EViewGroup(R.layout.listitem_pantip_pick)
public class PantipPickView extends RelativeLayout implements View.OnClickListener{

    @ViewById
    ImageView thumbnail;
    @ViewById
    TextView title;
    @ViewById
    TextView author;
    @ViewById
    TextView tags;
    @ViewById
    View read;
    @ViewById
    RelativeLayout root;
    @ViewById
    CardView card;

    private Topic topic;
    private Context context;

    public PantipPickView(Context context) {
        super(context);
        this.context = context;

    }

    @AfterViews
    void init(){
        root.setOnClickListener(this);
    }

    public void bind(Topic topic){
        this.topic = topic;
        title.setText(Html.fromHtml(topic.getTitle()));
        author.setText(topic.getAuthor()!=null?topic.getAuthor():topic.getName());

        StringBuilder sb = new StringBuilder();
        for (Tag tag : topic.getTags()) {
            sb.append(tag.getTag() + ", ");
        }
        try {
            tags.setText(sb.substring(0, sb.length() - 2));
        } catch (Exception e) {
        }

        if(topic.getCoverImg()!=null && topic.getCoverImg().length()>0) {
            thumbnail.setVisibility(VISIBLE);
            Picasso.with(context).load(topic.getCoverImg()).placeholder(R.drawable.ic_image).resize(400, 300).centerCrop().into(thumbnail);
        }else{
            thumbnail.setVisibility(GONE);
        }

    }



    @Override
    public void onClick(View v) {
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        BaseApplication.getEventBus().post(new OpenTopicEvent(topic, startingLocation[1]));
    }
}
