package epsi.utilisateur;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import epsi.utilisateur.models.Match;


public class MatchAdapter extends ArrayAdapter<Match> {
    Context context;
    int resourceId;
    List<Match> matchs;

    public MatchAdapter(Context _context, int _resource, List<Match> _objects) {
        super(_context, _resource, _objects);
        context = _context;
        resourceId = _resource;
        matchs = _objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MatchHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resourceId, parent, false);

            holder = new MatchHolder();
            holder.flag1 = (ImageView)row.findViewById(R.id.flag1);
            holder.flag2 = (ImageView)row.findViewById(R.id.flag2);
            holder.court = (TextView)row.findViewById(R.id.court_lib);
            holder.j1 = (TextView)row.findViewById(R.id.joueur1);
            holder.j2 = (TextView)row.findViewById(R.id.joueur2);

            row.setTag(holder);
        }
        else
        {
            holder = (MatchHolder)row.getTag();
        }

        Match m = matchs.get(position);
        holder.flag1.setImageResource( context.getResources().getIdentifier(m.getJoueur1().getNationalite().toLowerCase().trim(), "drawable", context.getPackageName()));
        holder.flag2.setImageResource( context.getResources().getIdentifier(m.getJoueur2().getNationalite().toLowerCase().trim(), "drawable", context.getPackageName()));

        holder.court.setText(m.getCourt());
        holder.j1.setText( m.getJoueur1().getFullName());
        holder.j2.setText( m.getJoueur2().getFullName());
        return row;
    }

    static class MatchHolder
    {
        ImageView flag1;
        ImageView flag2;
        TextView court;
        TextView j1;
        TextView j2;
    }

}
