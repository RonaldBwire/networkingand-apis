public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Post> postList;

    public MyAdapter(List<Post> postList) {
        this.postList = postList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, bodyTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.titleTextView);
            bodyTextView = view.findViewById(R.id.bodyTextView);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.titleTextView.setText(post.title);
        holder.bodyTextView.setText(post.body);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
