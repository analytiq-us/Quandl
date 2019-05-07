package org.eod;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "QuandlEOD" Node.
 * 
 *
 * @author I Tang Lo
 */
public class QuandlEod__NodeFactory 
        extends NodeFactory<QuandlEod__NodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public QuandlEod__NodeModel createNodeModel() {
        return new QuandlEod__NodeModel();
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
    public NodeView<QuandlEod__NodeModel> createNodeView(final int viewIndex,
            final QuandlEod__NodeModel nodeModel) {
        return null;
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
        return new QuandlEod__NodeDialog();
    }

}

