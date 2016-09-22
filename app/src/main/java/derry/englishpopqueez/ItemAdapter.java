package derry.englishpopqueez;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<Level> levels;
    Context context;
    MediaPlayer mp;

    public ItemAdapter(Context _context, ArrayList<Level> _levels) {
        levels = _levels;
        context = _context;
        mp = MediaPlayer.create(_context, R.raw.button);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.levelNumber.setText(levels.get(position).getLevelName());
        int score = levels.get(position).getStars();
        switch (score){
            case 0:
                holder.star1.setImageResource(R.drawable.ic_star_border);
                holder.star2.setImageResource(R.drawable.ic_star_border);
                holder.star3.setImageResource(R.drawable.ic_star_border);
                break;
            case 1:
                holder.star2.setImageResource(R.drawable.ic_star_border);
                holder.star3.setImageResource(R.drawable.ic_star_border);
                break;
            case 2:
                holder.star3.setImageResource(R.drawable.ic_star_border);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView levelNumber;
        ImageView star1, star2, star3;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            levelNumber = (TextView) view.findViewById(R.id.level_number);
            star1 = (ImageView) view.findViewById(R.id.bintang1);
            star2 = (ImageView) view.findViewById(R.id.bintang2);
            star3 = (ImageView) view.findViewById(R.id.bintang3);
        }

        @Override
        public void onClick(View view) {
            //todo send level index bundle to next activity
            Intent intent = new Intent(view.getContext(), QuestionActivity.class);
            intent.putExtra("position",getLayoutPosition());
            view.getContext().startActivity(intent);
            Activity a = (Activity) view.getContext();
            a.finish();
        }
    }
}
