package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import com.airfrance.squalecommon.datatransfertobject.component.AuditGridDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ResultsDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.FactorsResultListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une liste de résultats en fonction de facteurs
 */
public class FactorsResultListTransformer extends AbstractListTransformer {

    /**
      * @param pObject le tableau de ProjectDTO à transformer en formulaires.
      * @throws WTransformerException si un pb apparaît.
      * @return le formulaire associé
      */
    public WActionForm objToForm(Object[] pObject) throws WTransformerException {
        ResultListForm form = new ResultListForm();
        objToForm(pObject, form);
        return form;
    }

    /**
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm(Object[] pObject, WActionForm pForm) throws WTransformerException {
        Collection applications = (Collection) pObject[0];
        Collection results = (Collection) pObject[1];
        ResultListForm form = (ResultListForm) pForm;
        // On fait un parcours de la liste des projets
        // pour en extraire toutes les grilles
        Collection grids = extractGrids(results);

        // On fait une map par grille existante
        Hashtable gridsForm = new Hashtable();
        Iterator gridsIt = grids.iterator();
        while (gridsIt.hasNext()) {
            // Création du formulaire qui regroupe les résultats autour d'une même
            // grille
            FactorsResultListForm factorsForm = new FactorsResultListForm();
            QualityGridDTO grid = (QualityGridDTO) gridsIt.next();
            factorsForm.setFactors(grid.getFactors());
            factorsForm.setGridName(grid.getName());
            factorsForm.setGridUpdateDate(grid.getUpdateDate());
            gridsForm.put(grid, factorsForm);
        }
        form.setList(new ArrayList(gridsForm.values()));

        // On parcourt maintenant chaque résultat et en fonction du type de grille
        // on ajoute des données dans le form correspondant

        // On parcourt la liste des résultats projet par projet
        Iterator projectResultsIterator = results.iterator();
        while (projectResultsIterator.hasNext()) {
            List projectResults = (List) projectResultsIterator.next();
            /*#########################################
             * Mise en forme des résultats
             */
            // Le premier élément de la liste correspond au dernier audit
            Map latestResults = ((ResultsDTO) projectResults.get(0)).getResultMap();
            // On supprime la liste des facteurs renvoyée par la méthode
            // avec null comme clef
            latestResults.remove(null);
            // On trie la map pour avoir le même ordre dans les résultats
            // des audits.
            latestResults = new TreeMap(latestResults);
            Map preLatestResults = null;
            // L'élément suivant s'il existe correspond à l'avant dernier
            // audit et permettra d'obtenir des tendances
            if (projectResults.size() > 1) {
                preLatestResults = ((ResultsDTO) projectResults.get(1)).getResultMap();
                // On supprime la liste des facteurs renvoyée avec la clef
                // null
                preLatestResults.remove(null);
            }

            Iterator auditGridterator = latestResults.entrySet().iterator();
            // On parcourt chacun des projets pour mettre
            // en forme les résultats
            while (auditGridterator.hasNext()) {
                Map.Entry entry = (Entry) auditGridterator.next();
                AuditGridDTO auditGrid = (AuditGridDTO) entry.getKey();
                ComponentDTO component = auditGrid.getProject();
                // Java bean contenant les résultats
                ProjectFactorsForm bean = new ProjectFactorsForm(component);
                // On affecte l'id de l'audit courant au form
                bean.setCurrentAuditId("" + auditGrid.getAudit().getID());
                bean.setApplicationId("" + component.getIDParent());
                bean.setApplicationName(TransformerUtils.getApplicationName(component.getIDParent(), applications));
                ArrayList params = new ArrayList();
                params.add(entry);
                // Ajout des tendances à passer en paramètre
                Map.Entry prevEntry = getPreviousResult(component, preLatestResults);
                if (prevEntry!=null) {
                    params.add(prevEntry);
                }
                // Conversion welcome
                WTransformerFactory.objToForm(ProjectFactorsTransformer.class, bean, new Object[] { component, params });
                // Récupération de la grille et placement de celle-ci
                FactorsResultListForm f = (FactorsResultListForm) gridsForm.get(auditGrid.getGrid());
                f.getResults().add(bean);
                // si il y a des résultats comparable, on l'indique
                form.setComparableAudits(bean.getComparableAudits());
            }
        }
    }

    /**
     * Obtention du résultat précédent
     * @param pComponent composant
     * @param pPreviousResults résultat précédent ou null si non présents
     * @return résultat précédent ou null si non existant
     */
    private Map.Entry getPreviousResult(ComponentDTO pComponent, Map pPreviousResults) {
        Map.Entry result = null;
        if (pPreviousResults!=null) {
            Iterator it = pPreviousResults.entrySet().iterator();
            // On cherche dans les résultats la grille qui correspond au composant
            while (it.hasNext()&&(result==null)) {
                Map.Entry prevEntry = (Entry) it.next();
                AuditGridDTO prevAuditGrid = (AuditGridDTO) prevEntry.getKey();
                if (prevAuditGrid.getProject().getID()==pComponent.getID()) {
                    result = prevEntry;
                }
            }
        }
        return result;
    }
    
    /**
     * Obtention des grilles qualité
     * @param pResults résultats à explorer
     * @return ensemble des grilles qualité
     */
    private Collection extractGrids(Collection pResults) {
        TreeSet grids = new TreeSet();
        // Le TreeSet contiendra la liste des grilles qualité utilisées
        // dans les résultats
        Iterator iterator = pResults.iterator();
        // Parcours des résultats par application
        // chaque application contient les résultats par projet
        while (iterator.hasNext()) {
            Iterator results = ((Collection) iterator.next()).iterator();
            // Parcours des résultats par projet et par audit
            // on se limite au dernier audit : la grille qualité des audits
            // précedents est inutile pour le calcul des tendances
            boolean lastaudit = true;
            while (results.hasNext() && lastaudit) {
                lastaudit = false;
                ResultsDTO result = (ResultsDTO) results.next();
                Iterator keys = result.getResultMap().keySet().iterator();
                // Parcours de chaque projet
                while (keys.hasNext()) {
                    Object key = keys.next();
                    if (key instanceof AuditGridDTO) {
                        AuditGridDTO dto = (AuditGridDTO) key;
                        // Ajout de la grille dans un set ce qui évite les duplications
                        grids.add(dto.getGrid());
                    }
                }
            }
        }
        return grids;
    }

    /**
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de ProjectDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj(WActionForm pForm, Object[] pObject) throws WTransformerException {
    }

}
