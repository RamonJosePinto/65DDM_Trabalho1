package com.project.a65ddm_trabalho1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.a65ddm_trabalho1.databinding.FragmentPesquisaMedicamentoApiBinding
import com.project.a65ddm_trabalho1.model.MedicamentoAPIAdapter
import com.project.a65ddm_trabalho1.model.api.MedicamentoAPI
import com.project.a65ddm_trabalho1.model.api.RespostaAPI
import com.project.a65ddm_trabalho1.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesquisaMedicamentoFragment : Fragment() {

    private var _binding: FragmentPesquisaMedicamentoApiBinding? = null
    private val binding get() = _binding!!

    private lateinit var retrofitInitializer: RetrofitInitializer
    private val medicamentos = mutableListOf<MedicamentoAPI>()
    private lateinit var adapter: MedicamentoAPIAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPesquisaMedicamentoApiBinding.inflate(inflater, container, false)
        retrofitInitializer = RetrofitInitializer()

        setupRecyclerView()
        setupSearchButton()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = MedicamentoAPIAdapter(medicamentos)
        binding.recyclerViewMedicamentos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMedicamentos.adapter = adapter
    }

    private fun setupSearchButton() {
        binding.buttonPesquisar.setOnClickListener {
            val nomeMedicamento = binding.editTextNomeMedicamento.text.toString()
            if (nomeMedicamento.isNotBlank()) {
                medicamentos.clear()
                searchMedicamentos(nomeMedicamento)
            } else {
                Toast.makeText(requireContext(), "Digite o nome do medicamento", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchMedicamentos(nome: String) {
        var pagina = 1
        val service = retrofitInitializer.create()

        fun fetchPage() {
            val call = service.pesquisarMedicamentos(nome, pagina)
            call.enqueue(object : Callback<RespostaAPI> {
                override fun onResponse(call: Call<RespostaAPI>, response: Response<RespostaAPI>) {

                    if (response.isSuccessful) {
                        response.body()?.let { respostaAPI ->
                            medicamentos.addAll(respostaAPI.content)
                            adapter.notifyDataSetChanged()

                            if (respostaAPI.content.isNotEmpty()) {
                                pagina++
                                fetchPage()
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RespostaAPI>, t: Throwable) {
                    System.out.println(t.toString());
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        fetchPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
