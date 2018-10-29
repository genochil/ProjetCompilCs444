# Makefile pour ima

EXECUTABLES = test_pseudo_code ima

OPTIONS = -g -gnato -i -aO../Obj

all : 
	@if test "${EXECUTABLES}" != "" ;\
        then \
           for P in ${EXECUTABLES} ; do \
              echo gnatmake $$P ;\
              gnatmake ${OPTIONS} $$P ;\
              mv $$P ../Exec ;\
           done ;\
        fi
	mv *.o ../Obj
	mv *.ali ../Obj

clean :
	cd ../Obj ; /bin/rm -f *.o *.ali b~.ad?

allclean : clean
	cd ../Exec ; /bin/rm ${EXECUTABLES}

