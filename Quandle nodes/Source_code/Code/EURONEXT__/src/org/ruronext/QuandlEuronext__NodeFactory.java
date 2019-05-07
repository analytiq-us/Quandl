package org.ruronext;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "QuandlEURONEXT" Node.
 * 
 *
 * @author Heng Chi
 */
public class QuandlEuronext__NodeFactory 
        extends NodeFactory<QuandlEuronext__NodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public QuandlEuronext__NodeModel createNodeModel() {
        return new QuandlEuronext__NodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<QuandlEuronext__NodeModel> createNodeView(final int viewIndex,
            final QuandlEuronext__NodeModel nodeModel) {
        return new QuandlEuronext__NodeView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new QuandlEuronext__NodeDialog();
    }

}

