package lvhong.tim.atm.adapter;

import java.util.ArrayList;

import lvhong.tim.atm.R;
import lvhong.tim.atm.data.ATMNode;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ATMArrayAdapter extends ArrayAdapter<ATMNode>{
	private final ArrayList<ATMNode> atm_list;
	private final Activity context;
	
	 public ATMArrayAdapter(Activity context, ArrayList<ATMNode> atm_list) {
		 	super(context, R.layout.atmnodelist, atm_list);
		 	this.context = context;
		 	this.atm_list = atm_list;
     }
	 
	static class ViewHolder {
		protected TextView txt_tendd, txt_diachi;
        protected ImageView icon;
     }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.atmnodelist, null);
			final ViewHolder viewholder = new ViewHolder();
			viewholder.icon = (ImageView)view.findViewById(R.id.img_icon);
			viewholder.txt_diachi = (TextView)view.findViewById(R.id.txt_diachi);
			viewholder.txt_tendd = (TextView)view.findViewById(R.id.txt_tendd);
			view.setTag(viewholder);	
		}
		else{
			view = convertView;
		}
		ViewHolder holder = (ViewHolder) view.getTag();
        holder.txt_tendd.setText(atm_list.get(position).get_tendd());
        holder.txt_diachi.setText(atm_list.get(position).get_diachi());
	    return view;
	}
	
}
