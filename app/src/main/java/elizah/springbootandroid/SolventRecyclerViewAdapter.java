package elizah.springbootandroid;

/**
 * Created by elh on 18.09.17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventViewHolders> {

    private List<ItemObjects> itemList;
    private Context context;

    public SolventRecyclerViewAdapter(Context context, List<ItemObjects> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
// create a new view
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        SolventViewHolders rcv = new SolventViewHolders(layoutView, itemList);
        return rcv;
    }

    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {

        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        holder.listDescription.setText(itemList.get(position).getDescription());
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setTag(itemList.get(position).getPhoto());
        boolean like = itemList.get(position).getIsfav();
        if(like == true){
            holder.likeImageView.setImageResource(R.drawable.ic_liked);
        }
        else{ holder.likeImageView.setImageResource(R.drawable.ic_like);}
        holder.likeImageView.setTag(like);

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}