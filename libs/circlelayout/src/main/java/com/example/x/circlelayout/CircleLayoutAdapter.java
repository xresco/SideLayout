package com.example.x.circlelayout;

import android.content.Context;

/**
 * Created by x on 11/2/2014.
 */
public abstract class CircleLayoutAdapter {


    //private LinkedList<Integer> adapter=new LinkedList<Integer>();
    protected Context context;
    protected CircleLayout parent;
    public void setContext(Context context) {
        this.context = context;
    }

    public  void setParent(CircleLayout parent)
    {
        this.parent = parent;
    }

    public abstract void add(Object image);
//    {
//        adapter.add(image);
//        if (parent!=null)
//         parent.init();
//
//    }

    public abstract CircularLayoutItem get(int index);
//    {
//        Log.d("Index1::", index + "");
//       return context.getResources().getDrawable( adapter.get(getRoundedIndex(index)) );
//    }

    public abstract Integer getRoundedIndex(Integer index);
//    {
//         int new_index=((-1*index)%adapter.size()+adapter.size())%adapter.size();
//        return new_index;
//    }

}
