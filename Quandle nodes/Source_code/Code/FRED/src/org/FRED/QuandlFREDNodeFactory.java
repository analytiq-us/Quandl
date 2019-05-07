package org.FRED;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "QuandlFRED" Node.
 * 
 *
 * @author VIDHYA
 */
public class QuandlFREDNodeFactory 
        extends NodeFactory<QuandlFREDNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public QuandlFREDNodeModel createNodeModel() {
        return new QuandlFREDNodeModel();
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
    public NodeView<QuandlFREDNodeModel> createNodeView(final int viewIndex,
            final QuandlFREDNodeModel nodeModel) {
        return new QuandlFREDNodeView(nodeModel);
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
        return new QuandlFREDNodeDialog();
    }

}

