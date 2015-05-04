package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

/**
 * Created by Shenno on 28/04/2015.
 */
public interface OnSelectedListener {
    public void onItemClicked(int position);
    public void onDossierItemClicked(int id);
    public void onDmsItemClicked(int id);
    public void onNewDossierClicked(int id);
    public void onAddExtraToDossier(int id);
    public void onAddLocationToDossier(int id);
}
