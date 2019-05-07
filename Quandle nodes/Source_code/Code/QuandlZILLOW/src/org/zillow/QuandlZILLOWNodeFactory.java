package org.zillow;


import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "QuandlZILLOW" Node.
 * 
 *
 * @author 
 * Sampreeth
 */
public class QuandlZILLOWNodeFactory 
        extends NodeFactory<QuandlZILLOWNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public QuandlZILLOWNodeModel createNodeModel() {
        return new QuandlZILLOWNodeModel();
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
    public NodeView<QuandlZILLOWNodeModel> createNodeView(final int viewIndex,
            final QuandlZILLOWNodeModel nodeModel) {
        return new QuandlZILLOWNodeView(nodeModel);
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
        return new QuandlZILLOWNodeDialog();
    }

}

