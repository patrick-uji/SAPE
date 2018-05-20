package es.uji.ei1027.sape.domain;
import java.util.List;
public class OffersSelection
{
	private List<Integer> selectedOffers;
	public List<Integer> getSelectedOffers()
	{
		return selectedOffers;
	}
	public void setSelectedOffers(List<Integer> selectedOffers)
	{
		this.selectedOffers = selectedOffers;
	}
}
