package com.plusplus.i.jongerenparticipatieplatfrom.fragments;

/**
 * Created by Shenno on 28/04/2015.
 */
public interface OnSelectedListener {
    public void onItemClicked(int position);
    public void onDossierItemClicked(int id);
    public void onDmsItemClicked(int id, String answer);
    public void onNewDossierClicked(int id);
    public void onAddExtraToDossier(int id);
    public void onAddLocationToDossier(int id);
    public void onAddEventToDossier(int id);
    public void onHomeAmsItemClicked(int id);
    public void onAmsItemClicked(int id, String question);
    public void onReactionItemClicked(int id);
    public void onVoteDossier(int id);
    public void onVoteAgendaReaction(int id);
    public void onEditDossierClicked(int id);
}
