package com.pace.cs639spring.hw2;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<AnimalItem> mAnimalList;
    private OnItemClickListener mListener;

    // store current selected position
    private int selectedPosition = -1;

    //use 0 and 1 to store Button state off and on
    private int rotateButtonState = 0;
    private int flipButtonState = 0;
    private int arrowBackButtonState = 0;
    private int arrowForwardButtonState = 0;
    private int arrowUpButtonState = 0;
    private int arrowDownButtonState = 0;
    private int centerButtonState = 0;
    private float dptoPX10;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onNextFactClick(int position);
        void onDeleteFactClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;
        public Button nextButton;
        public Button deleteButton;


        public ExampleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.animalImage);
            mTextView = itemView.findViewById(R.id.animalText);
            nextButton = itemView.findViewById(R.id.nextButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(view ->  {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
            });

            nextButton.setOnClickListener(view ->  {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onNextFactClick(position);
                        }
                    }
            });

            deleteButton.setOnClickListener(view ->  {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteFactClick(position);
                        }
                    }
            });

        }
    }

    public ExampleAdapter(ArrayList<AnimalItem> animalList){
        mAnimalList = animalList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_item, parent, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v, mListener);
        return exampleViewHolder;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        AnimalItem currentItem = mAnimalList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView.setText(currentItem.getText());
        holder.mTextView.setVisibility(position == selectedPosition ? View.VISIBLE : View.INVISIBLE);
        holder.nextButton.setVisibility(position == selectedPosition ? View.VISIBLE : View.INVISIBLE);
        holder.deleteButton.setVisibility(position == selectedPosition ? View.VISIBLE : View.INVISIBLE);

        if(rotateButtonState == 1 && position == selectedPosition) {
            holder.mImageView.setRotation(holder.mImageView.getRotation()%360 + 90);
            rotateButtonState =0;
        }
        if(flipButtonState == 1 && position == selectedPosition) {
            holder.mImageView.setRotationY(holder.mImageView.getRotationY() % 360 + 180);
            flipButtonState =0;
        }
        if(arrowBackButtonState == 1 && position == selectedPosition) {
            holder.mImageView.setTranslationX(holder.mImageView.getTranslationX() - dptoPX10);
            arrowBackButtonState =0;
        }
        if(arrowForwardButtonState == 1 && position == selectedPosition) {
            holder.mImageView.setTranslationX(holder.mImageView.getTranslationX() + dptoPX10);
            arrowForwardButtonState =0;
        }
        if(arrowUpButtonState == 1 && position == selectedPosition) {
            holder.mImageView.setTranslationY(holder.mImageView.getTranslationY() - dptoPX10);
            arrowUpButtonState =0;
        }
        if(arrowDownButtonState == 1 && position == selectedPosition) {
            holder.mImageView.setTranslationY(holder.mImageView.getTranslationY() + dptoPX10);
            arrowDownButtonState =0;
        }
        if(centerButtonState == 1 && position == selectedPosition) {
            holder.mImageView.setTranslationX(0);
            holder.mImageView.setTranslationY(0);
            centerButtonState =0;
        }
    }

    @Override
    public int getItemCount() {
        return mAnimalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // notify the change of each item could also achieve the function but cause icon blink.
    public void togglePosition(int position) {
        if (selectedPosition != position) {
            selectedPosition = position;
        } else {
            selectedPosition = -1;
        }
        notifyDataSetChanged();
    }


    public int getSelectedPosition(){return selectedPosition;}

    // change the button state to 1 every time a button get clicked
    public void changeButtonState(int buttonId) {
            resetButton();
           switch (buttonId) {
               case R.id.rotateButton:
                   rotateButtonState = 1;
                   break;
               case R.id.flipButton:
                   flipButtonState = 1;
                   break;
               case R.id.arrowBack:
                   arrowBackButtonState = 1;
                   break;
               case R.id.arrowForward:
                   arrowForwardButtonState = 1;
                   break;
               case R.id.arrowUpward:
                   arrowUpButtonState = 1;
                   break;
               case R.id.arrowDownward:
                   arrowDownButtonState = 1;
                   break;
               case R.id.vectorCenter:
                   centerButtonState = 1;
                   break;
           }

        notifyDataSetChanged();
    }
// reset button state to 0
    public void resetButton(){
        rotateButtonState = 0;
        flipButtonState = 0;
        arrowBackButtonState = 0;
        arrowForwardButtonState = 0;
        arrowUpButtonState = 0;
        arrowDownButtonState = 0;
        centerButtonState = 0;
    }

    public float get10dpPX(float pX){
        dptoPX10 = pX;
        return dptoPX10;
    }

}
