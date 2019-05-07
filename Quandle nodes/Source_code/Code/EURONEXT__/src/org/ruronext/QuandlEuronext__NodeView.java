package org.ruronext;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "QuandlEURONEXT" Node.
 * 
 *
 * @author Heng Chi
 */
public class QuandlEuronext__NodeView extends NodeView<QuandlEuronext__NodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link QuandlEuronext__NodeModel})
     */
    protected QuandlEuronext__NodeView(final QuandlEuronext__NodeModel nodeModel) {
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
        QuandlEuronext__NodeModel nodeModel = 
            (QuandlEuronext__NodeModel)getNodeModel();
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

