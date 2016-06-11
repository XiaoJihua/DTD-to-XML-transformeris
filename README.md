DTD-to-XML-transformeris
========================

This is a simple commandline-based tool to convert XML's DTD specification into XML schema (.xsd file).
Works with either embeded DTD in an xml as well as DTD in a referenced foreign file.

Transformeris does NOT support:

* specification of entities
* wrongly written DTD's
* DTD's where attributes of element are defined before element itself (who would do this, really)
