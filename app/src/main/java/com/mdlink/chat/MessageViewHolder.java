package com.mdlink.chat;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdlink.App;
import com.mdlink.R;
import com.twilio.chat.Member;
import com.twilio.chat.Message;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.message_item_layout)
public class MessageViewHolder extends ItemViewHolder<MessageActivity.MessageItem>
{
    private static int[] HORIZON_COLORS = {
        Color.GRAY, Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA
    };

    @ViewId(R.id.avatar)
    ImageView imageView;

    @ViewId(R.id.reachability)
    ImageView reachabilityView;

    @ViewId(R.id.body)
    TextView body;

    @ViewId(R.id.author)
    TextView author;

    @ViewId(R.id.date)
    TextView date;

    @ViewId(R.id.consumptionHorizonIdentities)
    RelativeLayout identities;

    @ViewId(R.id.consumptionHorizonLines)
    LinearLayout lines;

    View view;

    public MessageViewHolder(View view)
    {
        super(view);
        this.view = view;
    }

    @Override
    public void onSetListeners()
    {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                OnMessageClickListener listener = getListener(OnMessageClickListener.class);
                if (listener != null) {
                    listener.onMessageClicked(getItem());
                    return true;
                }
                return false;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                date.setVisibility(date.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onSetValues(final MessageActivity.MessageItem message, PositionInfo pos)
    {
        if (message != null) {
            final Message msg = message.getMessage();

            author.setText(msg.getAuthor());
            body.setText(msg.getMessageBody());
            //date.setText(msg.getTimeStamp());

            identities.removeAllViews();
            lines.removeAllViews();

            for (Member member : message.getMembers().getMembersList()) {

                if (member.getLastConsumedMessageIndex() != null
                        && member.getLastConsumedMessageIndex()
                        == message.getMessage().getMessageIndex()) {
                    drawConsumptionHorizon(member);
                }
            }
        }
    }

    private void drawConsumptionHorizon(Member member)
    {
        String ident = member.getIdentity();
        int color = getMemberRgb(ident);

        TextView identity = new TextView(getContext());
        identity.setText(ident);
        identity.setTextSize(8);
        identity.setTextColor(color);

        // Layout
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int cc = identities.getChildCount();
        if (cc > 0) {
            params.addRule(RelativeLayout.RIGHT_OF, identities.getChildAt(cc - 1).getId());
        }
        identity.setLayoutParams(params);

        View line = new View(getContext());
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5));
        line.setBackgroundColor(color);

        identities.addView(identity);
        lines.addView(line);

    }

    public interface OnMessageClickListener {
        void onMessageClicked(MessageActivity.MessageItem message);
    }

    public int getMemberRgb(String identity)
    {
        return HORIZON_COLORS[Math.abs(identity.hashCode()) % HORIZON_COLORS.length];
    }
}