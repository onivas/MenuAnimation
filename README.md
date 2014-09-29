# Menu Animation 
##Floating Action Button (FAB) based on Material Design

By Savino Ordine - io.github.onivas.promotedactions.PromotedActionsLibrary;

This is a Lib allow you to create a Promoted Action menu placed on right|bottom corner of the screen.

![Promoted Action animation](https://github.com/onivas/MenuAnimation/blob/master/app/promotedAction.gif)

##How it works

#Import library
<pre>
import io.github.onivas.promotedactions.PromotedActionsLibrary;
</pre>

#Steps
<pre>
FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);

PromotedActionsLibrary promotedActionsLibrary = new PromotedActionsLibrary();

// setup library
promotedActionsLibrary.setup(getApplicationContext(), frameLayout);

// create onClickListener for each promoted action
View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
        public void onClick(View view) {
            // Do something
    }
};

// customize promoted actions with a drawable
promotedActionsLibrary.addItem(getResources().getDrawable(android.R.drawable.ic_menu_edit), onClickListener);
promotedActionsLibrary.addItem(getResources().getDrawable(android.R.drawable.ic_menu_send), onClickListener);
promotedActionsLibrary.addItem(getResources().getDrawable(android.R.drawable.ic_input_get), onClickListener);

// create main floating button and customize it with a drawable
promotedActionsLibrary.addMainItem(getResources().getDrawable(android.R.drawable.ic_input_add));

</pre>
