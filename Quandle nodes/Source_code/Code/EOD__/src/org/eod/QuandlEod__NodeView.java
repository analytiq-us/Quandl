package org.eod;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "QuandlEOD" Node.
 * 
 *
 * @author I Tang Lo
 */
public class QuandlEod__NodeView extends NodeView<QuandlEod__NodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link QuandlEod__NodeModel})
     */
    protected QuandlEod__NodeView(final QuandlEod__NodeModel nodeModel) {
        super(nodeModel);

        // TODO instantiate the components of the view here.

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {

        // TODO retrieve the new model from your nodemodel and 
        // update the view.
        QuandlEod__NodeModel nodeModel = 
            (QuandlEod__NodeModel)getNodeModel();
        assert nodeModel != null;
        
        // be aware of a possibly not executed nodeModel! The data you retrieve
        // from your nodemodel could be null, emtpy, or invalid in any kind.
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
    
        // TODO things to do when closing the view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {

        // TODO things to do when opening the view
    }

}

