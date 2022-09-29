package com.utopiaxc.utopiatts.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.preference.PreferenceManager;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.utopiaxc.utopiatts.MainActivity;
import com.utopiaxc.utopiatts.R;

public class FragmentAbout extends MaterialAboutFragment {

    @Override
    protected MaterialAboutList getMaterialAboutList(Context activityContext) {
        //app cards
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();

        //copyright card
        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .desc(R.string.rights)
                .icon(R.mipmap.ic_launcher)
                .build());

        //version card
        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(activityContext,
                new IconicsDrawable(activityContext)
                        .icon(CommunityMaterial.Icon.cmd_code_array)
                        .sizeDp(18),
                requireActivity().getString(R.string.version),
                true));

        //change log card
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.change_log)
                .icon(new IconicsDrawable(activityContext)
                        .icon(CommunityMaterial.Icon.cmd_content_paste)
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebViewDialogOnClickAction(
                        activityContext,
                        requireActivity().getString(R.string.change_log_title),
                        getString(R.string.github_release_link), true, false))
                .build());

        //license card
        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.licenses)
                .icon(new IconicsDrawable(activityContext)
                        .icon(CommunityMaterial.Icon.cmd_code_tags)
                        .sizeDp(18))
                .setOnClickAction(() -> {
                    //Intent intent = new Intent(activityContext, LicencesActivity.class);
                    //startActivity(intent);
                })
                .build());

        //author cards
        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title(R.string.author);

        //author card
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.author_name)
                .icon(new IconicsDrawable(activityContext)
                        .icon(CommunityMaterial.Icon.cmd_account)
                        .sizeDp(18))
                .build());

        //github card
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.follow_on_github)
                .icon(new IconicsDrawable(activityContext)
                        .icon(CommunityMaterial.Icon.cmd_github_circle)
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebsiteOnClickAction(
                        activityContext, Uri.parse(getString(R.string.github_link))))
                .build());

        //email feedback card
        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(activityContext,
                new IconicsDrawable(activityContext)
                        .icon(CommunityMaterial.Icon.cmd_email)
                        .sizeDp(18),
                requireActivity().getString(R.string.feedback_by_email),
                true,
                getString(R.string.user_email),
                requireActivity().getString(R.string.feedback_subject)));

        //settings card
        MaterialAboutCard.Builder settingsCardBuilder = new MaterialAboutCard.Builder();
        settingsCardBuilder.title(R.string.settings);

        //clear profiles
        settingsCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.clear_all_profiles)
                .icon(new IconicsDrawable(activityContext)
                        .icon(CommunityMaterial.Icon.cmd_delete)
                        .sizeDp(18))
                .setOnClickAction(() -> new AlertDialog.Builder(getContext())
                        .setTitle(R.string.warning)
                        .setMessage(R.string.clear_all_profiles_confirm)
                        .setPositiveButton(R.string.confirm, (dialog, which) -> {
                            PreferenceManager.getDefaultSharedPreferences(requireActivity())
                                    .edit().clear().apply();
                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show())
                .build());
        return new MaterialAboutList(appCardBuilder.build(),
                authorCardBuilder.build(),
                settingsCardBuilder.build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}