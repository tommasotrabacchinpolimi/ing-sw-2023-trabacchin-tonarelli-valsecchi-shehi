package controller;

import model.BookShelf;

public enum CommonGoalFunction {

    CG1_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG2_FUCNTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            System.out.println( "common2" );
            return true;
        }
    },

    CG3_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG4_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG5_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG6_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG7_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG8_FUCNTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG9_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG10_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG11_FUCNTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    },

    CG12_FUNCTION{
        @Override
        public boolean execute( BookShelf bookShelf ){
            return true;
        }
    };

    abstract boolean execute( BookShelf bookShelf );
}
